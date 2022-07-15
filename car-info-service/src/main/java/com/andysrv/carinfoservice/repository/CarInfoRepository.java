package com.andysrv.carinfoservice.repository;

import com.andysrv.carinfoservice.entity.CarInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CarInfoRepository extends ReactiveMongoRepository<CarInfo, String> {
    Flux<CarInfo> findByPrice(Double price);

    Mono<CarInfo> findByTitleAndYear(String title, Integer year);
}
