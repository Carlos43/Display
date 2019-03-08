package com.dinfo.bdms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(ignoreUnknownFields = false, prefix = "bdms")
@Configuration
public class BDMSConfig {

    private String baseUrl;
    private String savePath;

//    private String hybridUrl;
//    private String hybridRoadUrl;
//    private String hybridSavePath;
//    private String hybridRoadSavePath;

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSavePath() {
        return savePath;
    }
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

//    public String getHybridUrl() {
//        return hybridUrl;
//    }
//    public void setHybridUrl(String hybridUrl) {
//        this.hybridUrl = hybridUrl;
//    }
//
//    public String getHybridRoadUrl() {
//        return hybridRoadUrl;
//    }
//    public void setHybridRoadUrl(String hybridRoadUrl) {
//        this.hybridRoadUrl = hybridRoadUrl;
//    }
//
//    public String getHybridSavePath() {
//        return hybridSavePath;
//    }
//    public void setHybridSavePath(String hybridSavePath) {
//        this.hybridSavePath = hybridSavePath;
//    }
//
//    public String getHybridRoadSavePath() {
//        return hybridRoadSavePath;
//    }
//    public void setHybridRoadSavePath(String hybridRoadSavePath) {
//        this.hybridRoadSavePath = hybridRoadSavePath;
//    }
}
