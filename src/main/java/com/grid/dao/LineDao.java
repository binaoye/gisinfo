package com.grid.dao;

import com.grid.Entity.LineEntity;
import com.grid.Entity.LineInspector;

import java.util.List;
import java.util.Map;

public interface LineDao {
    List<LineEntity> QueryLines(String city);
    Map<String,double[][]> QueryLinePoints(String line);
    Map<String,double[][]> QueryCityLinePoints(String city);
    List<LineInspector> QueryLineInspector(String line);
    Integer AddUser(LineInspector user);
    void UpdateUser(LineInspector user);
    void UserExists(LineInspector user);
}
