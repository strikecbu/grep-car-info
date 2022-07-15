package com.andysrv.carinfoservice.repository;

import com.andysrv.carinfoservice.entity.CarPrice;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
public interface CarPriceRepository extends ReactiveMongoRepository<CarPrice, String> {
    Flux<CarPrice> findByIdIn(Collection<String> id);
}
