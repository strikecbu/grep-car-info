package com.andy.grepcarinfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarView {

    private String title;
    private Double price;
    private String description;
    private String detailUrl;
    private Integer year;
    private String brand;
    private VendorType vendorType;
    private LocalDateTime queryTime;


}
