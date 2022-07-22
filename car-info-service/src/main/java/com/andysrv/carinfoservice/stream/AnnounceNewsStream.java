package com.andysrv.carinfoservice.stream;

import lombok.Data;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Data
@Component
public class AnnounceNewsStream {

    private Sinks.Many<String> sink;
    private Flux<String> newsFlux;

    public AnnounceNewsStream() {
        this.sink = Sinks.many().multicast().directBestEffort();
        this.newsFlux = this.sink.asFlux();
    }

    public Sinks.EmitResult next(String value) {
        return sink.tryEmitNext(value);
    }
}
