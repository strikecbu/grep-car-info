package com.andysrv.carinfoservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "carPrice")
@Data
@Builder
public class CarPrice {

    @Id
    private String id;

    private Double price;
    private LocalDateTime createTime;
}
