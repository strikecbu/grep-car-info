package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.UpdateInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectGrepDataServiceJsoupImplTest {

    @Test
    void transTextToCar() {
        List<String[]> list = new ArrayList<>();
        final UpdateInfo updateInfo = new UpdateInfo();
        final ConnectGrepDataServiceShouShiImpl service = new ConnectGrepDataServiceShouShiImpl(updateInfo);
        String[] sample1 = {"2020/2020 NISSAN SENTRA 四門 白色 1.6L 自排已收訂感恩深坑陳先生"};
        String[] sample2 =
                {"2020/2020 LEXUS NX300 F SPORT 五門 白色 2.0T 手自排 179.8萬 賞車按我",
                 "▲8SRS ABS IKEY 影音 防滑 ACC AEB ISOFIX 恆溫 胎壓監控 煞車輔助 里程1.8萬KM 原廠保固中 新車價格217萬按我參考"};
        list.add(sample1);
        list.add(sample2);

        List<Car> carList = new ArrayList<>();
        for (String[] strings : list) {
            final Car car = new Car();
            carList.add(car);
            for (String text : strings) {
                service.transTextToCar(car, "", text);
            }
        }

        for (Car car : carList) {
            assertNotNull(car.getName());
            if(!car.isSold()) {
                assertNotNull(car.getPrices().get(0).getPrice());
            }
        }

    }
}
