package com.grid.service;

import java.util.List;

public interface GeoService {
    List<String> GetCities();
    List<String> GetCounties(String city);
    List<String> GetStreets(String city, String county);
    List<String> GetVillages(String city, String county, String street);

}
