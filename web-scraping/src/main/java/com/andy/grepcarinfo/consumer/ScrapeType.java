package com.andy.grepcarinfo.consumer;

import java.util.Arrays;
import java.util.Optional;

public enum ScrapeType {
    CAR;

    public static Optional<ScrapeType> values(String value) {
        return Arrays.stream(ScrapeType.values())
                .filter(type -> type.toString()
                        .equals(value))
                .findFirst();
    }
}
