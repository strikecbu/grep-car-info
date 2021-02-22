package com.andy.grepcarinfo.repository;

import com.andy.grepcarinfo.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/20 AndyChen,new
 * </ul>
 * @since 2021/2/20
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    Car findByName(String name);

}
