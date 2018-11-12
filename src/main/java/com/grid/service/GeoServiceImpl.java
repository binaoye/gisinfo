package com.grid.service;

import com.grid.dao.CItyDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("geoService")
public class GeoServiceImpl implements GeoService{
    @Resource
    private CItyDao dao;

    @Override
    public List<String> GetCities() {
        return dao.GetCities();
    }

    @Override
    public List<String> GetCounties(String city) {
        return dao.GetCounties(city);
    }

    @Override
    public List<String> GetStreets(String city, String county) {
        return dao.GetStreets(city, county);
    }

    @Override
    public List<String> GetVillages(String city, String county, String street) {
        return dao.GetVillages(city, county, street);
    }
}
