package com.grid.dao;

import com.grid.Entity.CityEntity;
import com.grid.Entity.GeoCache;

import java.util.List;

public interface CItyDao {
    List<CityEntity> Query();
    GeoCache GeoEncode(String city, String county, String street, String village);
}
