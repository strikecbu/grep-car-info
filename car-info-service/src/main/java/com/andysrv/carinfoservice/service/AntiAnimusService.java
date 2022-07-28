package com.andysrv.carinfoservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AntiAnimusService {

    private final ConcurrentMap<String, LocalDateTime> map = new ConcurrentHashMap<>();

    @Value("${carinfo.antiFrequency}")
    private Integer duringAllow;

    public boolean isHighFrequencyOperate(String key) {
        LocalDateTime latestTime = map.get(key);
        LocalDateTime now = LocalDateTime.now();
        if (latestTime == null) {
            map.put(key, now);
            return false;
        }

        long between = ChronoUnit.MINUTES.between(latestTime, now);
        if (between > duringAllow) {
            map.put(key, now);
            return false;
        }

        return true;
    }

}
