package com.zorba11.canadaopendataviz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="houses")
public class SoldHouse {

    @Id
    private long id;
    private String address;


    private String areaName;


    private long price;
    private double lat;
    private double lng;

    public SoldHouse() {
    }

    public SoldHouse(long id, String address, String areaName, long price, double lat, double lng) {
        this.id = id;
        this.address = address;
        this.areaName = areaName;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
