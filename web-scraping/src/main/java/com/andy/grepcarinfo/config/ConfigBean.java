package com.andy.grepcarinfo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/26 AndyChen,new
 * </ul>
 * @since 2021/2/26
 */
@Configuration
@ConfigurationProperties(prefix = "scrap.shoushi")
@Data
public class ConfigBean {

    private String url;

}
