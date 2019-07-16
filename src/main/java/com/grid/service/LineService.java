package com.grid.service;

import com.grid.Entity.CityEntity;
import com.grid.Entity.GeoCache;
import com.grid.Entity.LineEntity;
import com.grid.Entity.LineInspector;
import com.grid.Entity.LineFeature;

import java.util.List;
import java.util.Map;

public interface LineService {
    List<LineEntity> QueryAll(String city);
    List<CityEntity> QueryCity();
    Map<String,Object> QueryLinePoint(String line);
    Map<String,double[][]> QueryCityLinePoints(String city);
    GeoCache GeoEncode(String city, String county, String street, String village);
    List<LineInspector> QueryLineInspector(String line);
    Integer AddUser(LineInspector user);
    void UpdateUser(LineInspector user);
    void UserExists(LineInspector user);
    List<LineInspector> UserDownload(String[] users);
    Map<String,double[][]> ListLinePoints(String line);
    List<CityEntity> GetProvDepts();
    LineFeature QueryLineFeatures(String line);
    void deleteUsers(String[] users);

    List<LineInspector> QueryCityInspectors(String city);

    List<LineInspector> QueryAllInspectors();

    void delUser(String code);

    Double linelen(String line);
}
