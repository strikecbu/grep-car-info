package com.andysrv.carinfoservice.service;

import com.andysrv.carinfoservice.entity.CarInfo;
import com.andysrv.carinfoservice.entity.CarPrice;
import com.andysrv.carinfoservice.repository.CarInfoRepository;
import com.andysrv.carinfoservice.repository.CarPriceRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
                            .flatMap(car -> {
                                CarPrice price = CarPrice.builder()
                                        .price(carInfo.getPrice())
                                        .createTime(LocalDateTime.now())
                                        .build();
                                if (car.getId() != null) {
                                    return priceRepository.findById(car.getLatestPriceId()
                                                    .toHexString())
                                            .flatMap(priceObj -> {
                                                if (Objects.equals(price.getPrice(), priceObj.getPrice())) {
                                                    return Mono.just(priceObj);
                                                } else {
                                                    return priceRepository.save(price)
                                                            .doOnNext(p -> {
                                                                car.setLatestPriceId(new ObjectId(p.getId()));
                                                                car.getPriceIds()
                                                                        .add(new ObjectId(p.getId()));
                                                            });
                                                }
                                            })
                                            .flatMap(savePrice -> {
                                                car.setUpdateTime(LocalDateTime.now());
                                                return infoRepository.save(car);
                                            });
                                } else {
                                    return priceRepository.save(price)
                                            .flatMap(carPrice -> {
                                                car.setCreateTime(LocalDateTime.now());
                                                car.setUpdateTime(LocalDateTime.now());
                                                ObjectId priceId = new ObjectId(carPrice.getId());
                                                car.setPriceIds(List.of(priceId));
                                                car.setLatestPriceId(priceId);
                                                return infoRepository.save(car);
                                            });
                                }
                            });
                });
    }

    public Flux<CarInfo> getAllCarInfo() {
        return infoRepository.findAll()
                .flatMap(carInfo -> priceRepository.findByIdIn(objectIdToString(carInfo.getPriceIds()))
                        .collectList()
                        .map(prices -> {
                            ArrayList<CarPrice> list = prices.stream()
                                    .sorted(Comparator.comparing(CarPrice::getCreateTime))
                                    .collect(Collectors.toCollection(ArrayList::new));
                            carInfo.setPrices(list);
                            return carInfo;
                        }));
    }

    private List<String> objectIdToString(List<ObjectId> list) {
        return list.stream()
                .map(ObjectId::toHexString)
                .collect(Collectors.toList());
    }
}
