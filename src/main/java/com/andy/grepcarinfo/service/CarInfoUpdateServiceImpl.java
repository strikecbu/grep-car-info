package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.Price;
import com.andy.grepcarinfo.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CarInfoUpdateServiceImpl implements CarInfoUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarInfoUpdateServiceImpl.class);

    final private ConnectGrepDataService grepDataService;
    final private CarRepository carRepository;

    public CarInfoUpdateServiceImpl(@Qualifier("grepDataJsoupService") ConnectGrepDataService grepDataService, CarRepository carRepository) {
        this.grepDataService = grepDataService;
        this.carRepository = carRepository;
    }


    @Override
    @Transactional
    public void updateShiouShiCar() throws IOException {
        long start = System.currentTimeMillis();
        final String url = "https://mama1978777.pixnet.net/blog/post/96336596";
        LOGGER.info("取得目標URL: {}", url);
        final List<Car> cars = grepDataService.grepCarData(url);

        for (Car car : cars) {
            final String carName = car.getName();
            final Car existCar = carRepository.findByName(carName);
            if (existCar != null) { //舊車更新資料
                LOGGER.info("檢查舊車資料: {}", existCar.getName());
                if (!existCar.isSold() && car.isSold()) {
                    existCar.setSold(car.isSold());
                    LOGGER.info("變更為已售出");
                }
                final List<Price> oldPrices = existCar.getPrices();
                final List<Price> newPrices = car.getPrices();
                if (newPrices.size() > 0) {
                    final Price price = newPrices.get(0);
                    // 如果價格和前次一樣就不更新
                    if (oldPrices.size() == 0 || !oldPrices.get(oldPrices.size() - 1).getPrice().equals(price.getPrice())) {
                        price.setCar(existCar);
                        oldPrices.add(price);
                        LOGGER.info("價格更新為: {}萬", price.getPrice());
                        existCar.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    }
                }
                carRepository.save(existCar);
            } else {
                LOGGER.info("儲存新車資料: {}", car.getName());
                carRepository.save(car);
            }
        }
        long end = System.currentTimeMillis();
        LOGGER.info("Car information update complete, total spend {} ms.", end - start);
    }
}
