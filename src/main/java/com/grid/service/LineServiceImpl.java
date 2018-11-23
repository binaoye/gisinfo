package com.grid.service;

import com.grid.Entity.CityEntity;
import com.grid.Entity.GeoCache;
import com.grid.Entity.LineEntity;
import com.grid.Entity.LineInspector;
import com.grid.dao.CItyDao;
import com.grid.dao.LineDao;
import com.grid.Entity.LineFeature;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("lineService")
public class LineServiceImpl implements LineService {
    @Resource
    private LineDao ldao;
    @Resource
    private CItyDao cdao;
    @Override
    public List<LineEntity> QueryAll(String city) {
        return this.ldao.QueryLines(city);
    }

    public List<CityEntity> QueryCity() {
        return this.cdao.Query();
    }

    @Override
    public Map<String, Object> QueryLinePoint(String line) {
        return this.ldao.QueryLinePoints(line);
    }

    @Override
    public Map<String, double[][]> QueryCityLinePoints(String city) {
        return this.ldao.QueryCityLinePoints(city);
    }

    @Override
    public GeoCache GeoEncode(String city, String county, String street, String village) {
        return this.cdao.GeoEncode(city, county, street, village);
    }

    @Override
    public List<LineInspector> QueryLineInspector(String line) {
        return ldao.QueryLineInspector(line);
    }

    @Override
    public Integer AddUser(LineInspector user) {
        return ldao.AddUser(user);
    }

    @Override
    public void UpdateUser(LineInspector user) {
        ldao.UpdateUser(user);
    }

    @Override
    public void UserExists(LineInspector user) {
        ldao.UserExists(user);
    }

    @Override
    public List<LineInspector> UserDownload(String[] users) {
        return ldao.DownUsers(users);
    }

    @Override
    public Map<String, double[][]> ListLinePoints(String line) {
        return ldao.ListLinePoints(line);
    }

    @Override
    public List<CityEntity> GetProvDepts() {
        return cdao.GetProvDept();
    }

    @Override
    public LineFeature QueryLineFeatures(String line) {
        return ldao.QueryLineFeature(line);
    }

    @Override
    public void deleteUsers(String[] users) {
        ldao.deleteUsers(users);
    }
}
