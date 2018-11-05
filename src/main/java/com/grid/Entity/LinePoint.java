package com.grid.Entity;

public class LinePoint {
    private Integer order;
    private String name;
    private double lat;
    private double lng;
    private String ssxl;
    private Integer GTPLXH;

    public Integer getGTPLXH() {
        return GTPLXH;
    }

    public void setGTPLXH(Integer GTPLXH) {
        this.GTPLXH = GTPLXH;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSsxl() {
        return ssxl;
    }

    public void setSsxl(String ssxl) {
        this.ssxl = ssxl;
    }
}
