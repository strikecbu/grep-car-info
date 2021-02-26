package com.andy.grepcarinfo.schedule;

import com.andy.grepcarinfo.service.CarInfoUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Time;

@Component
public class UpdateCarInfoSchedule {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateCarInfoSchedule.class);
    private final CarInfoUpdateService carInfoUpdateService;

    public UpdateCarInfoSchedule(CarInfoUpdateService carInfoUpdateService) {
        LOGGER.info("Update car info schedule is on.");
        this.carInfoUpdateService = carInfoUpdateService;
    }

    // 啟動後10秒更新，之後每1小時更新
    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void update() throws IOException {
        carInfoUpdateService.updateShiouShiCar();
    }

}
