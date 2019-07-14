package com.grid.controllers;

import com.grid.Entity.LineInspector;
import com.grid.dal.domain.Inspectors;
import com.grid.dal.domain.LineUsers;
import com.grid.dao.UserDao;
import com.grid.service.GeoService;
import com.grid.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.grid.utils.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class GaodeController {
//    @Autowired
//    private LineService lins;

    @Autowired
    private GeoService gserv;

    @Autowired
    private UserDao userDao;

    /**
     * 查询第一级区划
     * @return
     */
    @RequestMapping("/querycities")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object QueryCity() {
        Map<String,Object> result = new HashMap<String, Object>();
        System.out.println("-----");
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
    public Object AddUser(String name, String birth, String nation, String sex, String address, String code,String line, Short inside, double lat, double lng,double distance) {
//        System.out.println("添加用户线路:"+line);
//        LineInspector li = new LineInspector(name, birth, nation, sex, address, code, line, lat, lng,inside);
////        li.setDistance(distance);
////        li.setInside(inside);
//        Integer id = lins.AddUser(li);
//        Map<String, Object> result = new HashMap<String,Object>();
//        result.put("id", id);
        Map<String, Object> result = new HashMap<String, Object>();
        /*
        先判断用户是否已存在， 如果存在添加关联记录，否则先添加人员再添加关联记录
         */
        com.grid.dal.domain.LineInspector example = new com.grid.dal.domain.LineInspector();
        example.setCode(code);
        example.setLat(new BigDecimal(lat));
        example.setLng(new BigDecimal(lat));
        example.setName(name);
        example.setBirth(birth);
        example.setNation(nation);
        example.setSex(sex);
        example.setAddress(address);
        System.out.println(example.getCode());
        com.grid.dal.domain.LineInspector inspector = userDao.exists(example);
        // 添加关联记录
        LineUsers lineUsers = new LineUsers();
        lineUsers.setDistance(new BigDecimal(distance));
        lineUsers.setInside(inside);
        lineUsers.setInsname(inspector.getName());
        Long li = Long.valueOf(line);
        lineUsers.setLine(li);
        lineUsers.setInspector(inspector.getId());
        int resu = userDao.AddUserLine(lineUsers);
        if(resu < 1) {
            result.put("id", "-1");
        }
        result.put("id", inspector.getId());

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
//        List<LineInspector> lst = tran(lins.QueryLineInspector(line));
        Long li = Long.valueOf(line);
        System.out.println(li);
        List<Inspectors> inspectors = userDao.listLineUsers(li);

        Map<String,Object> result = new HashMap<String, Object>();
        result.put("users", inspectors);
        result.put("count", inspectors.size());
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
