package com.andy.grepcarinfo.config;

import com.andy.grepcarinfo.model.UpdateInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/26 AndyChen,new
 * </ul>
 * @since 2021/2/26
 */
@Configuration
@EnableScheduling
public class ConfigBean {

    @Bean
    public UpdateInfo updateInfo() {
        return new UpdateInfo();
    }
}
