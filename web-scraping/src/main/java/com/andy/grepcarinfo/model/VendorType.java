package com.andy.grepcarinfo.model;

import com.andy.grepcarinfo.service.ConnectGrepDataService2000Impl;
import com.andy.grepcarinfo.service.ConnectGrepDataServiceShouShiImpl;

public enum VendorType {

    SHOU_SHI("https://sscars.com.tw/car/", ConnectGrepDataServiceShouShiImpl.class),
    TWO_THOUSAND(
            "https://www.2000car.tw/blog/post/317462829-%E6%A1%83%E5%9C%92%E4%B8%AD%E5%A3%A2%E6%97%A5%E6%A6%AE%E6%B1%BD%E8%BB%8A-%E5%85%A9%E5%8D%83%E4%B8%AD%E5%8F%A4%E8%BB%8A%E8%BB%8A%E6%BA%90%E4%B8%80%E8%A6%BD%E8%A1%A8/blog/post/96336596", ConnectGrepDataService2000Impl.class);

    private String url;
    private Class grepDateServiceClass;
    VendorType(String url, Class grepDateServiceClass) {
        this.url = url;
        this.grepDateServiceClass = grepDateServiceClass;
    }

    public String getUrl() {
        return url;
    }

    public Class getGrepDateServiceClass() {
        return grepDateServiceClass;
    }

    public void setUpdateDate() {

    }

}