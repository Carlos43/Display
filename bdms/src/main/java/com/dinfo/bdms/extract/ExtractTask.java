package com.dinfo.bdms.extract;

import com.dinfo.bdms.utils.SpringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractTask implements Runnable {

    private String threadName;
    private List<String> urlList = new ArrayList<String>();
    private List<String> errList = new ArrayList<String>();
    private String fileSuffix = ".png";

    public ExtractTask(String threadName, List<String> urlList) {
        this.threadName = threadName;
        if(urlList != null) {
            this.urlList = urlList;
        }
    }

    @Override
    public void run() {
        try {
            for(int i=0; i<urlList.size(); i++) {
                System.out.println(threadName + " --> Extract Process: " + (i+1) + " / " + urlList.size());
                String url = urlList.get(i);

                boolean result = download(url);
                if(!result) {
                    errList.add(url);
                }
            }

            if(errList.size() != 0) {
                for(int i=0; i<errList.size(); i++) {
                    boolean result = download(errList.get(i));
                    if(!result) {
                        System.out.println(Thread.currentThread().getName() + " --> Error: " + errList.get(i));
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean download(String urlStr) {
        boolean result = false;
        InputStream is = null;
        OutputStream os = null;
        try {
            Map<String, String> fileIndexMap = getFileIndex(urlStr);

            if(!fileIndexMap.isEmpty()) {
                String x = fileIndexMap.get("x");
                String y = fileIndexMap.get("y");
                String z = fileIndexMap.get("z");
                if(x.equals("-1")) {
                    x = "M1";
                }
                if(y.equals("-1")) {
                    y = "M1";
                }
                if(x.equals("-2")) {
                    x = "M2";
                }
                if(y.equals("-2")) {
                    y = "M2";
                }
                String fileName = x + "_" + y + fileSuffix;

                String savePath = SpringUtils.getConfig().getSavePath();
                File file = new File(savePath + z + "/" + x);
                if(!file.exists()) {
                    file.mkdirs();
                }

                URL url = new URL(urlStr);
                URLConnection con = url.openConnection();
                con.setConnectTimeout(5*1000);
                is = con.getInputStream();
                byte[] bs = new byte[4096];
                int length;
                os = new FileOutputStream(savePath + z + "/" + x + "/" + fileName);
                while((length=is.read(bs)) != -1) {
                    os.write(bs, 0, length);
                }
            }
            result = true;
        } catch(Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if(os != null) {
                    os.flush();
                    os.close();
                    os = null;
                }
                if(is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private Map<String, String> getFileIndex(String urlStr) {
        Map<String, String> map = new HashMap<String, String>();
        String[] urlParts = urlStr.split("/");
        String fileParamStr;
        if(urlParts.length != 0) {
            fileParamStr = urlParts[urlParts.length-1];
            String[] fileParams = fileParamStr.split("&");
            for(int i=0; i<fileParams.length; i++) {
                if(fileParams[i].startsWith("x=")) {
                    map.put("x", fileParams[i].split("=")[1]);
                }
                if(fileParams[i].startsWith("y=")) {
                    map.put("y", fileParams[i].split("=")[1]);
                }
                if(fileParams[i].startsWith("z=")) {
                    map.put("z", fileParams[i].split("=")[1]);
                }
            }
        }
        return map;
    }
}
