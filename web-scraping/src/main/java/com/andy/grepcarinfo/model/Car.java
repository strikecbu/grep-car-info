package com.andy.grepcarinfo.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/20 AndyChen,new
 * </ul>
 * @since 2021/2/20
 */
@Entity
@Table(name = "CAR")
public class Car {

    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String vendor;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String name;

    private String year;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @CreationTimestamp
    private Timestamp initTime;

    @UpdateTimestamp
    private Timestamp updateTime;

    @OneToMany(mappedBy = "car", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Price> prices = new ArrayList<>();

    private boolean isSold;

    @Lob
    private String picUrl;

    @Lob
    private String newCarUrl;

    @Transient
    private Double latestPrice;
    @Transient
    private Long DayOfSold;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getInitTime() {
        return initTime;
    }

    public void setInitTime(Timestamp initTime) {
        this.initTime = initTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getNewCarUrl() {
        return newCarUrl;
    }

    public void setNewCarUrl(String newCarUrl) {
        this.newCarUrl = newCarUrl;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public Long getDayOfSold() {
        return DayOfSold;
    }

    public void setDayOfSold(Long dayOfSold) {
        DayOfSold = dayOfSold;
    }
}
