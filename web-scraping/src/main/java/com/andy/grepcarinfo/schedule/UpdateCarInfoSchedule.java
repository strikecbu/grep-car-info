package com.andy.grepcarinfo.schedule;

import com.andy.grepcarinfo.model.VendorType;
import com.andy.grepcarinfo.service.CarInfoUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateCarInfoSchedule {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateCarInfoSchedule.class);
    private final CarInfoUpdateService shouShiUpdateService;

    public UpdateCarInfoSchedule(@Qualifier("carInfoUpdateServiceImpl") CarInfoUpdateService shouShiUpdateService) {
        LOGGER.debug("Update car info schedule is on.");
        this.shouShiUpdateService = shouShiUpdateService;
    }

    // 啟動後10秒更新，之後每1小時更新
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void update() throws IOException {
        shouShiUpdateService.updateCarInfo(VendorType.SHOU_SHI);
        shouShiUpdateService.updateCarInfo(VendorType.TWO_THOUSAND);
    }

}
