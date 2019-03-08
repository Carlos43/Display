package com.dinfo.bdms.district;

import com.dinfo.bdms.utils.JSONUtil;
import com.dinfo.bdms.utils.OKManager;

public class DistrictBorderExtracter {

    private String url = "https://map.baidu.com/";

    public String extractBorder(String districtName) {
        OKManager okManager = new OKManager();
        String uid = getUid(okManager, districtName);
        String border = getBorder(okManager, uid);
        return border;
    }

//    private Map<String, String> getCityInfo(OKManager okManager, String cityName) {
//        Map<String, String> map = new HashMap<String, String>();
//        try {
//            String baseParam = "qt=s&wd=%s&auth=7xFY4b0KHSA69dCCB17ZLFT5S6Dv5F01uxHBxTTBRLRt1qo6DF==C1GgvPUDZYOYIZuVt1cv3uVtPWv3GuxtVwi04960vy777777777uWvPYuxt8zv7u@ZPuVteuVteh33uxts99Xv7";
//
//            baseParam = String.format(baseParam, cityName);
//            String requestUrl = url + "?" + baseParam;
//            String json = okManager.syncGet(requestUrl, null).body().string();
//            CityJsonEntity cityJsonEntity = (CityJsonEntity) JSONUtil.toBean(json, CityJsonEntity.class);
//            String cname = cityJsonEntity.getContent().getCname();
//            String code = cityJsonEntity.getContent().getCode();
//
//            map.put("cname", cname);
//            map.put("code", code);
//            return map;
//        } catch(Exception e) {
//            e.printStackTrace();
//            return map;
//        }
//    }

    private String getUid(OKManager okManager, String districtName) {
        String uid = null;
        try {
            //String baseParam = "biz=1&from=webmap&da_par=direct&pcevaname=pc4.1&qt=s&da_src=searchBox.button&wd=%s&c=131&src=0&wd2=&pn=0&sug=0&l=15&b=(12954909,4841249;12965837,4846297)&from=webmap&biz_forward={\"scaler\":1,\"styles\":\"pl\"}&sug_forward=&tn=B_NORMAL_MAP&nn=0";
            String baseParam = "qt=s&wd=%s";

            baseParam = String.format(baseParam, districtName);
            String requestUrl = url + "?" + baseParam;
            String json = okManager.syncGet(requestUrl, null).body().string();
            UidJsonEntity uidJsonEntity = JSONUtil.toBean(json, UidJsonEntity.class);
            uid = uidJsonEntity.getContent().getUid();
            return uid;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getBorder(OKManager okManager, String uid) {
        String border = null;
        try {
            String baseParam = "qt=ext&l=10&uid=%s";

            baseParam = String.format(baseParam, uid);
            String requestUrl = url + "?" + baseParam;
            String json = okManager.syncGet(requestUrl, null).body().string();
            border = JSONUtil.toBean(json, GeoJsonEntity.class).getContent().getGeo();
            return border;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}