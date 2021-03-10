package com.andy.grepcarinfo.service;

import java.io.IOException;

public interface CarInfoUpdateService {

    enum Vendor {
        SHOU_SHI("https://mama1978777.pixnet.net/blog/post/96336596", ConnectGrepDataServiceJsoupImpl.class),
        TWO_THOUSAND(
                "https://www.2000car.tw/blog/post/317462829-%E6%A1%83%E5%9C%92%E4%B8%AD%E5%A3%A2%E6%97%A5%E6%A6%AE%E6%B1%BD%E8%BB%8A-%E5%85%A9%E5%8D%83%E4%B8%AD%E5%8F%A4%E8%BB%8A%E8%BB%8A%E6%BA%90%E4%B8%80%E8%A6%BD%E8%A1%A8/blog/post/96336596", ConnectGrep2000DataService.class);

        private String url;
        private Class grepDateServiceClass;
        Vendor(String url, Class grepDateServiceClass) {
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

    void updateCarInfo(Vendor vendor) throws IOException;
}
