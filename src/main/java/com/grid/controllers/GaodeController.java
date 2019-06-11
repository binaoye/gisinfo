package com.grid.controllers;

import com.grid.Entity.LineInspector;
import com.grid.service.GeoService;
import com.grid.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    /**
     * 查询第二级别区划
     * @param city
     * @return
     */
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

    /**
     * 查询第三级别区划
     * @param city
     * @param county
     * @return
     */
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

    /**
     * 查询第四级别区划
     * @param city
     * @param county
     * @param street
     * @return
     */
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


    /**
     * 添加线路人员数据
     * @param name
     * @param birth
     * @param nation
     * @param sex
     * @param address
     * @param code
     * @param line
     * @param inside
     * @param lat
     * @param lng
     * @param distance
     * @return
     */
    @RequestMapping("/adduser")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object AddUser(String name, String birth, String nation, String sex, String address, String code,String line, Integer inside, double lat, double lng,double distance) {
        System.out.println("添加用户线路:"+line);
        LineInspector li = new LineInspector(name, birth, nation, sex, address, code, line, lat, lng,inside);
//        li.setDistance(distance);
//        li.setInside(inside);
        Integer id = lins.AddUser(li);
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("id", id);
        return result;
    }


    /**
     * 查询线路所有人员
     * @param line
     * @return
     */
    @RequestMapping("/queryinspector")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object QueryInspector(String line) {
        List<LineInspector> lst = tran(lins.QueryLineInspector(line));
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("users", lst);
        result.put("count", lst.size());
        return result;
    }

    public List<LineInspector> tran(List<LineInspector> lst) {
        List<LineInspector> resu = new ArrayList<>();
        for(LineInspector ls:lst) {
            LineInspector li = ls;
//            li.setDistance(ls.getDistance()/1000);
            resu.add(li);
        }
        return resu;

    }





}
