package com.grid.Entity;

public class GeoCache {
    private Integer id;
    private String city;
    private String county;
    private String street;
    private String village;
    private double latitude;
    private double longitude;
    private double latfloat;
    private double lngfloat;
    private Integer latint;
    private Integer lngint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatfloat() {
        return latfloat;
    }

    public void setLatfloat(double latfloat) {
        this.latfloat = latfloat;
    }

    public double getLngfloat() {
        return lngfloat;
    }

    public void setLngfloat(double lngfloat) {
        this.lngfloat = lngfloat;
    }

    public Integer getLatint() {
        return latint;
    }

    public void setLatint(Integer latint) {
        this.latint = latint;
    }

    public Integer getLngint() {
        return lngint;
    }

    public void setLngint(Integer lngint) {
        this.lngint = lngint;
    }
}
