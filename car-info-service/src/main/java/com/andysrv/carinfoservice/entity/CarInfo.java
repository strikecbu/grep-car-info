package com.andysrv.carinfoservice.entity;

import com.andysrv.carinfoservice.dto.VendorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(value = "carInfo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo {
    @Id
    private String id;

    private String title;
    private List<ObjectId> priceIds;
    private ObjectId latestPriceId;
    private String description;
    private String detailUrl;
    private String imageUrl;
    private Integer year;
    private String brand;
    private VendorType vendorType;
    private LocalDateTime queryTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Transient
    private Double price;
    @Transient
    private List<CarPrice> prices;
    @Transient
    private CarPrice latestPrice;

}
