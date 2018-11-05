package com.grid.dao;

import com.grid.Entity.LineEntity;

import java.util.List;
import java.util.Map;

public interface LineDao {
    List<LineEntity> QueryLines(String city);
    Map<String,double[][]> QueryLinePoints(String line);
    Map<String,double[][]> QueryCityLinePoints(String city);

}
