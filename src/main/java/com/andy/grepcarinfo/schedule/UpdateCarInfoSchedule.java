package com.andy.grepcarinfo.schedule;

import com.andy.grepcarinfo.service.CarInfoUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateCarInfoSchedule {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateCarInfoSchedule.class);
    private final CarInfoUpdateService carInfoUpdateService;

    public UpdateCarInfoSchedule(CarInfoUpdateService carInfoUpdateService) {
        LOGGER.info("Update car info schedule is on.");
        this.carInfoUpdateService = carInfoUpdateService;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void update() throws IOException {
        carInfoUpdateService.updateShiouShiCar();
    }

}
