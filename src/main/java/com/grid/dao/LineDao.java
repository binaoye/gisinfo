package com.grid.dao;

import com.grid.Entity.LineEntity;
import com.grid.Entity.LineFeature;
import com.grid.Entity.LineInspector;

import java.util.List;
import java.util.Map;

public interface LineDao {
    List<LineEntity> QueryLines(String city);
    Map<String,Object> QueryLinePoints(String line);
    Map<String,double[][]> QueryCityLinePoints(String city);
    List<LineInspector> QueryLineInspector(String line);
    Integer AddUser(LineInspector user);
    void UpdateUser(LineInspector user);
    void UserExists(LineInspector user);
    List<LineInspector> DownUsers(String[] users);
    Map<String,double[][]> ListLinePoints(String line);
    LineFeature QueryLineFeature(String line);
    GeoCache getCache();
    void deleteUsers(String[] users);
}
