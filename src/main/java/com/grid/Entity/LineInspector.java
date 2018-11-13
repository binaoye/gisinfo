package com.grid.Entity;

public class LineInspector {
    private Integer id;
    private String name;
    private String birth;
    private String nation;
    private String sex;
    private String address;
    private String code;
    private String line;
    private Integer inside;
    private double lat;
    private double lng;
    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public Integer getInside() {
        return inside;
    }

    public void setInside(Integer inside) {
        this.inside = inside;
    }

    public LineInspector(String name, String birth, String nation, String sex, String address, String code, String line, double lat, double lng, int inside) {
        this.name = name;
        this.birth = birth;
        this.nation = nation;
        this.sex = sex;
        this.address = address;
        this.code = code;
        this.line = line;
        this.lat = lat;
        this.lng = lng;
        this.inside = inside;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
