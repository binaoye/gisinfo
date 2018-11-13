package com.grid.service;

import com.grid.Entity.CityEntity;
import com.grid.Entity.GeoCache;
import com.grid.Entity.LineEntity;
import com.grid.Entity.LineInspector;

import java.util.List;
import java.util.Map;

public interface LineService {
    List<LineEntity> QueryAll(String city);
    List<CityEntity> QueryCity();
    Map<String,double[][]> QueryLinePoint(String line);
    Map<String,double[][]> QueryCityLinePoints(String city);
    GeoCache GeoEncode(String city, String county, String street, String village);
    List<LineInspector> QueryLineInspector(String line);
    Integer AddUser(LineInspector user);
    void UpdateUser(LineInspector user);
    void UserExists(LineInspector user);
    List<LineInspector> UserDownload(String[] users);
}
