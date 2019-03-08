package com.dinfo.bdms.extract;

import com.dinfo.bdms.utils.SpringUtils;

import java.util.ArrayList;
import java.util.List;

public class ExtractTaskDispatcher {

    public enum TaskType { TILE, BOUNDARY};

    public void dispatch(int minX, int maxX, int minY, int maxY, int level, boolean useMultiThread, int maxUrlPerThread) {
        List<Integer> xRange = initRangeList(minX, maxX);
        List<Integer> yRange = initRangeList(minY, maxY);

        List<String> urlList = initUrlList(xRange, yRange, level);
        System.out.println("Total Tiles Size: " + urlList.size());
        if(urlList != null && urlList.size() != 0) {
            if(useMultiThread) {
                int threadNum = 1;
                if(urlList.size()%maxUrlPerThread == 0) {
                    threadNum = urlList.size() / maxUrlPerThread;
                } else {
                    threadNum = (urlList.size() / maxUrlPerThread) + 1;
                }

                System.out.println("Total Thread Num: " + threadNum);
                for(int i=0; i<threadNum; i++) {
                    List<String> subUrlList = new ArrayList<String>();
                    if((i+1)*maxUrlPerThread < urlList.size()) {
                        subUrlList = urlList.subList(i*maxUrlPerThread, (i+1)*maxUrlPerThread);
                    } else {
                        subUrlList = urlList.subList(i*maxUrlPerThread, urlList.size());
                    }
                    ExtractTask extractTask = new ExtractTask("Thread "+i, subUrlList);
                    ThreadPool.addTask(extractTask);
                }
            } else {
                ExtractTask extractTask = new ExtractTask("Thread 0", urlList);
                ThreadPool.addTask(extractTask);
            }
        }
    }

    private List<Integer> initRangeList(int min, int max) {
        List<Integer> rangeList = new ArrayList<Integer>();
        for(int m=min; m<=max; m++) {
            rangeList.add(m);
        }
        return rangeList;
    }

    private List<String> initUrlList(List<Integer> xRange, List<Integer> yRange, int level) {
        List<String> urlList = new ArrayList<String>();
        String baseUrl = SpringUtils.getConfig().getBaseUrl();

        for(int i=0; i<xRange.size(); i++) {
            int x = xRange.get(i);
            for(int j=0; j<yRange.size(); j++) {
                int y = yRange.get(j);
                String url = String.format(baseUrl, x, y, level);
                urlList.add(url);
            }
        }
        return urlList;
    }
}
