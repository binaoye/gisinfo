package com.grid.dao;

import com.grid.Entity.CityEntity;
import com.grid.Entity.GeoCache;

import java.util.List;

public interface CItyDao {
    List<CityEntity> Query();
    GeoCache GeoEncode(String city, String county, String street, String village);
    List<String> GetCities();
    List<String> GetCounties(String city);
    List<String> GetStreets(String city, String county);
    List<String> GetVillages(String city, String county, String street);
    List<CityEntity> GetProvDept();

}
