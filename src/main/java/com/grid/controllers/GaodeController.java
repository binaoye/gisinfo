package com.grid.controllers;

import com.grid.Entity.LineInspector;
import com.grid.service.GeoService;
import com.grid.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.grid.utils.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class GaodeController {
    @Autowired
    private LineService lins;

    @Autowired
    private GeoService gserv;

    /**
     * 查询第一级区划
     * @return
     */
    @RequestMapping("/querycities")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object QueryCity() {
        Map<String,Object> result = new HashMap<String, Object>();
        List lst = gserv.GetCities();
        result.put("result", lst);
        result.put("count", lst.size());
        return result;
    }

    @RequestMapping("/querycounties")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object QueryCounties(String city) {
        Map<String, Object> result = new HashMap<String, Object>();
        List lst = gserv.GetCounties(city);
        result.put("result", lst);
        result.put("count", lst.size());
        return result;
    }


    @RequestMapping("/querystreets")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object QueryStreets(String city, String county) {
        Map<String, Object> result = new HashMap<String, Object>();
        List lst = gserv.GetStreets(city, county);
        result.put("result", lst);
        result.put("count", lst.size());
        return result;
    }

    @RequestMapping("/queryvillages")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object QueryVillages(String city, String county, String street) {
        Map<String, Object> result = new HashMap<String, Object>();
        List lst = gserv.GetVillages(city, county, street);
        result.put("result", lst);
        result.put("count", lst.size());
        return result;
    }


    @RequestMapping("/adduser")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object AddUser(String name, String birth, String nation, String sex, String address, String code,String line, Integer inside, double lat, double lng) {
        LineInspector li = new LineInspector(name, birth, nation, sex, address, code, line, lat, lng,inside);
        li.setInside(inside);
        Integer id = lins.AddUser(li);
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("id", id);
        return result;
    }

    @RequestMapping("/queryinspector")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object QueryInspector(String line) {
        List<LineInspector> lst = lins.QueryLineInspector(line);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("users", lst);
        result.put("count", lst.size());
        return result;
    }





}
