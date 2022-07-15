package com.andysrv.carinfoservice.service;

import com.andysrv.carinfoservice.entity.CarInfo;
import com.andysrv.carinfoservice.entity.CarPrice;
import com.andysrv.carinfoservice.repository.CarInfoRepository;
import com.andysrv.carinfoservice.repository.CarPriceRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarInfoService {

    private final CarInfoRepository infoRepository;
    private final CarPriceRepository priceRepository;

    public CarInfoService(CarInfoRepository carInfoRepository, CarPriceRepository carPriceRepository) {
        this.infoRepository = carInfoRepository;
        this.priceRepository = carPriceRepository;
    }

    public Mono<CarInfo> saveOrUpdate(CarInfo originCarInfo) {
        return Mono.just(originCarInfo)
                .flatMap(carInfo -> {
                    return infoRepository.findByTitleAndYear(carInfo.getTitle(), carInfo.getYear())
                            .defaultIfEmpty(carInfo)
                            .flatMap(updateCar -> {
                                CarPrice price = CarPrice.builder()
                                        .price(carInfo.getPrice())
                                        .createTime(LocalDateTime.now())
                                        .build();
                                return priceRepository.save(price)
                                        .flatMap(carPrice -> {
                                            if (updateCar.getId() != null) {
                                                updateCar.setUpdateTime(LocalDateTime.now());
                                                updateCar.getPriceIds()
                                                        .add(carPrice.getId());
                                                return infoRepository.save(updateCar);
                                            } else {
                                                carInfo.setCreateTime(LocalDateTime.now());
                                                carInfo.setUpdateTime(LocalDateTime.now());
                                                carInfo.setPriceIds(List.of(carPrice.getId()));
                                                return infoRepository.save(carInfo);
                                            }
                                        });

                            });
                });
    }

    public Flux<CarInfo> getAllCarInfo() {
        return infoRepository.findAll()
                .flatMap(carInfo -> priceRepository.findByIdIn(carInfo.getPriceIds())
                        .collectList()
                        .map(prices -> {
                            ArrayList<CarPrice> list = prices.stream()
                                    .sorted(Comparator.comparing(CarPrice::getCreateTime))
                                    .collect(Collectors.toCollection(ArrayList::new));
                            carInfo.setPrices(list);
                            return carInfo;
                        }));
    }
}
