package com.andy.grepcarinfo.handler;

import com.andy.grepcarinfo.config.ConfigBean;
import com.andy.grepcarinfo.model.ConfigView;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ConfigHandler {

    private final ConfigBean configBean;

    public ConfigHandler(ConfigBean configBean) {
        this.configBean = configBean;
    }

    public Mono<ServerResponse> getConfigs(ServerRequest request) {
        ConfigView configView = ConfigView.builder()
                .url(configBean.getUrl())
                .build();
        return ServerResponse.ok().bodyValue(configView);
    }
}
