package com.andy.grepcarinfo.runner;

import com.andy.grepcarinfo.service.CarInfoUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/20 AndyChen,new
 * </ul>
 * @since 2021/2/20
 */
@Component
public class StartRunner implements CommandLineRunner {

    final private CarInfoUpdateService carInfoUpdateService;

    public StartRunner(CarInfoUpdateService carInfoUpdateService) {
        this.carInfoUpdateService = carInfoUpdateService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 轉交給schedule操作
//        carInfoUpdateService.updateShiouShiCar();
    }
}
