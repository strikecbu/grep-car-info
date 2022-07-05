package com.andy.grepcarinfo.repository;

import com.andy.grepcarinfo.model.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/22 AndyChen,new
 * </ul>
 * @since 2021/2/22
 */
@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {
}
