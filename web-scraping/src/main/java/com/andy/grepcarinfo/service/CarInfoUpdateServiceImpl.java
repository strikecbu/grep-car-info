package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.Price;
import com.andy.grepcarinfo.model.VendorType;
import com.andy.grepcarinfo.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarInfoUpdateServiceImpl implements CarInfoUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarInfoUpdateServiceImpl.class);

    final private ApplicationContext applicationContext;
    final private CarRepository carRepository;

    public CarInfoUpdateServiceImpl(ApplicationContext applicationContext, CarRepository carRepository) {
        this.applicationContext = applicationContext;
        this.carRepository = carRepository;
    }


    @Override
    @Transactional
    public void updateCarInfo(VendorType vendor) throws IOException {
        long start = System.currentTimeMillis();
        final String url = vendor.getUrl();
        LOGGER.info("取得目標URL: {}", url);
        final String[] beans = applicationContext.getBeanNamesForType(ConnectGrepDataService.class);
        ConnectGrepDataService grepDataService = null;
        for (String beanName : beans) {
            final String className = vendor.getGrepDateServiceClass().getSimpleName();
            if (className.equalsIgnoreCase(beanName)) {
                grepDataService = (ConnectGrepDataService) applicationContext.getBean(vendor.getGrepDateServiceClass());
                break;
            }
        }
//        final List<Car> cars = grepDataService.grepCarData(url);
        final List<Car> cars = new ArrayList<>();
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
