package com.grid.controllers;

import com.grid.Entity.GeoCache;
import com.grid.Entity.LineEntity;
import com.grid.service.LineService;
import com.grid.utils.LocationUtil;
import com.grid.utils.csvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class MapController {
    @Autowired
    private LineService lins;
    InputStream in = this.getClass().getResourceAsStream("/districts.csv");
    private Map<String, Map<String,List>> cache = csvUtil.readVillages(this.in);
    private double thres = 5000.0;
    @RequestMapping("/testdist")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object TestDist(String posi, String line) {
        Map<String,Object> result = new HashMap<String, Object>();
        // 抽取城市
        List citys = this.cache.get("city").get("city");
        String city = travel(posi, citys);
        posi = posi.replace(city,"");
        // 抽取县级
        List countys = this.cache.get("county").get("county");
        String county = travel(posi, countys);
        posi = posi.replace(county, "");
        // 抽取街道或乡镇
        List streets = this.cache.get("street").get(county);
        String street = travel(posi,streets);
        if (street.equals("")) {
            if(posi.contains("乡")){
                posi = posi.replace("乡","镇");
                street = travel(posi, streets);
            }else if(posi.contains("镇")){
                posi = posi.replace("乡","镇");
                street = travel(posi, streets);
            }

        }

        posi = posi.replace(street, "");
        posi = posi.replace("甘肃省", "");
        posi = posi.replace("行政", "");
        String vil = posi;
        if (posi.contains("社区")) {
            String[] sds = posi.split("社区");
            vil = sds[0];
        }
        if (posi.contains("村")) {
            String[] sds = posi.split("村");
            vil = sds[0];
        }
        // 抽取村
        List villages = this.cache.get("village").get(street);
        String village = travel(vil, villages);
        if((village.equals(""))&(vil.contains("家"))) {
            village = travel(vil.replace("家", ""), villages);
        }
        GeoCache ca = this.lins.GeoEncode(city,county,street,village);
        result.put("lat", ca.getLatitude());
        result.put("lng", ca.getLongitude());
        // 距离判定
        Map<String,double[][]> points = this.lins.QueryLinePoint(line);
        double[][] ps = points.get(line);
        double mindist = 1000000.0;
        for(double[] poi:ps) {
            double dist = LocationUtil.getDistance(poi[1],poi[0],ca.getLongitude(),ca.getLatitude());
            if(dist <= mindist) {
                mindist = dist;
            }
        }
        if(mindist <= thres) {
            result.put("result",1);
        }else {
            result.put("result",0);
        }
        return result;
    }

    /**
     * 查询市所有线路列表
     * @param city
     * @return
     */
    @RequestMapping("/getlines")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object QueryLines(String city) {
        System.out.println("查询城市"+city);
        List<LineEntity> lines = lins.QueryAll(city);
        return lines;
    }

    /**
     * 查询市级单位列表
     * @return
     */
    @RequestMapping("/getCities")
    @ResponseBody
    public Object QueryCities() {
        return this.lins.QueryCity();
    }

    /**
     * 查询线路所有点
     * @param line
     * @return
     */
    @RequestMapping("/getLinePoints")
    @ResponseBody
    public Object QueryLine(String line) {
        return this.lins.QueryLinePoint(line);
    }

    @RequestMapping("/queryCityLinePoints")
    @ResponseBody
    public Object QueryCityLinePoints(String city) {
        return this.lins.QueryCityLinePoints(city);
    }

    public String travel(String input, List list) {
        String result = "";
        System.out.println(input + list.size());
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            if(input.contains(next)&!next.equals("")) {
                System.out.println("1" + next);
                return next;
            }
        }
        String [] pl = input.split("");
        Iterator<String> newiterator = list.iterator();
        while(newiterator.hasNext()){
            String next = newiterator.next();
            String[] ls = next.split("");
            Boolean flag1 = true;
            Boolean flag2 = true;
            for(String l:ls) {
                if(!input.contains(l)) {
                    flag1 = false;
                    break;
                }
            }
            for(String l:pl) {
                if(!next.contains(l)) {
                    flag2 = false;
                    break;
                }
            }
            if (flag1 | flag2) {
                System.out.println("2" + next);
                return next;
            }
        }
        return result;
    }


    @RequestMapping("/setthres")
    @ResponseBody
    public Object SetThres(double thres) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("result", 0);
        this.thres = thres;
        return result;
    }

    @RequestMapping("/distcalc")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object DistCalc(String city, String county, String street, String village, String line) {
        Map<String,Object>  result = new HashMap<String, Object>();
        GeoCache ca = this.lins.GeoEncode(city,county,street,village);
        result.put("lat", ca.getLatitude());
        result.put("lng", ca.getLongitude());
        // 距离判定
        Map<String,double[][]> points = this.lins.QueryLinePoint(line);
        double[][] ps = points.get(line);
        double mindist = 1000000.0;
        for(double[] poi:ps) {
            double dist = LocationUtil.getDistance(poi[1],poi[0],ca.getLongitude(),ca.getLatitude());
            if(dist <= mindist) {
                mindist = dist;
            }
        }
        if(mindist <= thres) {
            result.put("result",1);
        }else {
            result.put("result",0);
        }
        result.put("distance", mindist);
        return result;
    }


}
