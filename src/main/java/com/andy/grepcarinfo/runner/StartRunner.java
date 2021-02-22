package com.andy.grepcarinfo.runner;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.Price;
import com.andy.grepcarinfo.repository.CarRepository;
import com.andy.grepcarinfo.repository.PriceRepository;
import com.andy.grepcarinfo.service.ConnectGrepDataService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/20 AndyChen,new
 * </ul>
 * @since 2021/2/20
 */
@Component
public class StartRunner implements CommandLineRunner {

    final ConnectGrepDataService grepDataService;
    final CarRepository carRepository;
    final PriceRepository priceRepository;

    public StartRunner(@Qualifier("grepDataJsoupService") ConnectGrepDataService grepDataService,
                       CarRepository carRepository, PriceRepository priceRepository) {
        this.grepDataService = grepDataService;
        this.carRepository = carRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        final Car car = new Car();
//        final Price price = new Price();
//        price.setPrice(23.9);
//        price.setCar(car);
//        final ArrayList<Price> priceList = new ArrayList<>();
//        priceList.add(price);
//        car.setPrices(priceList);
//        car.setName("Hi123");
//
//        repository.save(car);

        final String url = "https://mama1978777.pixnet.net/blog/post/96336596";
        final List<Car> cars = grepDataService.grepCarData(url);

        for (Car car : cars) {
            final String carName = car.getName();
            final Car existCar = carRepository.findByName(carName);
            if (existCar != null) { //舊車更新資料
                existCar.setSold(car.isSold());
                final List<Price> oldPrices = existCar.getPrices();
                final List<Price> newPrices = car.getPrices();
                if (newPrices.size() > 0) {
                    final Price price = newPrices.get(0);
                    if (oldPrices.size() > 0 && !oldPrices.get(oldPrices.size() - 1).getPrice().equals(price.getPrice())) {
                        price.setCar(existCar);
                        oldPrices.add(price);
                        existCar.setUpdateTime(LocalDateTime.now());
                    }
                }
                carRepository.save(existCar);
            } else {
                carRepository.save(car);
            }
        }
    }
}
