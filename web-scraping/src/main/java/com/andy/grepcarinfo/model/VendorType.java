package com.andy.grepcarinfo.model;


import java.util.Arrays;
import java.util.Optional;

public enum VendorType {

    SHOU_SHI,
    TWO_THOUSAND;

    public static Optional<VendorType> values(String value) {
        return Arrays.stream(VendorType.values())
                .filter(type -> type.toString()
                        .equals(value))
                .findFirst();
    }

}
