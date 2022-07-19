package com.andysrv.carinfoservice.dto;

import com.andysrv.carinfoservice.entity.CarPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarView {

    private String id;
    private String title;
    private List<CarPrice> prices;
    private CarPrice latestPrice;
    private String description;
    private String detailUrl;
    private String imageUrl;
    private Integer year;
    private String brand;
    private VendorType vendorType;
    private LocalDateTime queryTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
