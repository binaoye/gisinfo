package com.grid.controllers;

import com.grid.Entity.*;
import com.grid.dal.domain.LineUsers;
import com.grid.dao.UserDao;
import com.grid.service.LineService;
import com.grid.utils.LocationUtil;
import com.grid.utils.csvUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class MapController {
    @Autowired
    private LineService lins;
    @Autowired
    private UserDao userDao;
    InputStream in = this.getClass().getResourceAsStream("/districts.csv");
    private Map<String, Map<String,List>> cache = csvUtil.readVillages(this.in);
    private double thres = 5000.0;
    @RequestMapping("/testdist")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object TestDist(String posi, String line) {
        Map<String,Object> result = new HashMap<String, Object>();
        DistResult dr = new DistResult();
        dr = calcDist(posi, line);
        if(dr.getDist()>thres) {
            result.put("result", 0);
        }else {
            result.put("result", 1);
        }
        Double len = lins.linelen(line);
        result.put("lat", dr.getLat());
        result.put("lng", dr.getLng());
        result.put("distance", dr.getDist()/1000.0);
//        result.put("linelen", len);
        return result;
    }

    public DistResult calcDist(String posi, String line) {
        DistResult result = new DistResult();
        System.out.println("检查地址"+posi);
        if(posi.contains("甘肃省")){
            List citys = this.cache.get("city").get("city");
            posi = posi.replace("甘肃省","");
            String city = travel(posi, citys);
            System.out.println("citys"+citys.size()+city);
            posi = posi.replace(city,"");
            // 抽取县级
            List countys = this.cache.get("county").get("county");
            String county = travel(posi, countys);
            posi = posi.replace(county, "");
            // 抽取街道或乡镇
            System.out.println("county"+county);
            if(this.cache.get("street").containsKey(county)) {
                System.out.println("111");
            }else {
                System.out.println("000");
            }
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
            System.out.println(ca.getLatitude()+","+ca.getLongitude());
            result.setLat(ca.getLatitude());
            result.setLng(ca.getLongitude());
            result.setCity(city);
            result.setCounty(county);
            result.setStreet(street);
            result.setVillage(village);
            // 距离判定
            Map<String,double[][]> points = this.lins.ListLinePoints(line);
            double[][] ps = points.get(line);
            double mindist = 100000000.0;
            for(double[] poi:ps) {
                double dist = LocationUtil.getDistance(poi[1],poi[0],ca.getLongitude(),ca.getLatitude());
                if(dist <= mindist) {
                    mindist = dist;
                }
            }
            result.setDist(mindist);
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

    /**
     * 查询城市所有点
     * @param city
     * @return
     */
    @RequestMapping("/queryCityLinePoints")
    @ResponseBody
    public Object QueryCityLinePoints(String city) {
        return this.lins.QueryCityLinePoints(city);
    }

    public String travel(String input, List list) {
        String result = "";
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            if(input.contains(next)&!next.equals("")) {
//                System.out.println("1" + next);
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
//                System.out.println("2" + next);
                return next;
            }
        }
        return result;
    }


    /**
     * 判定阀值
     * @param thres
     * @return
     */
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
//        System.out.println(ca.getLatitude(),ca.getLongitude());
        // 距离判定
        Map<String,double[][]> points = this.lins.ListLinePoints(line);
        double[][] ps = points.get(line);
        System.out.println("总点数"+ps.length);
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

    @RequestMapping("/getProvDept")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object GetProvDept() {
        Map<String,Object>  result = new HashMap<String, Object>();
        List<CityEntity> cts = lins.GetProvDepts();
        result.put("result", cts);
        return result;
    }

    /**
     * 查询线路信息
     * @param line
     * @return
     */
    @RequestMapping("/queryLineFeature")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object QueryLineFeature(String line) {
        Map<String, Object> result = new HashMap<String, Object>();
        LineFeature lf = lins.QueryLineFeatures(line);
        result.put("result", lf);
        return result;
    }


    /**
     * 根据线路批量上传
     * @param file
     * @param line
     * @return
     */
    @RequestMapping("/updateusers")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object UpdateUsers(MultipartFile file, String line) {
        System.out.println("批量上传路线："+line);
        Map<String,Object> result = new HashMap<String,Object>();
        Workbook book = null;
        List<String> failList = new ArrayList<>();
        if (!file.isEmpty()) {
            try {
//                System.out.println("name"+ file.getOriginalFilename());
                String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                if (".xls".equals(ext)) {
                    book = new HSSFWorkbook(file.getInputStream());

                } else if (".xlsx".equals(ext)) {
                    book = new XSSFWorkbook(file.getInputStream());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (null != book) {
                // ID,姓名，性别，民族，地址，生日，号码
                Sheet sheet = book.getSheetAt(0);
                System.out.println("总长度"+sheet.getLastRowNum());
                for (int i = 0; i < sheet.getLastRowNum(); i++) {
//                    if(sheet.getRow(i).getCell(5) != null){
                    if(true){
                        i = i+ 1;
                        LineInspector li = new LineInspector();
                        Cell cell4 = sheet.getRow(i).getCell(5);//假如row.getCell(0)中的数值为12345678910123
                        cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
                        Cell cell5 = sheet.getRow(i).getCell(6);//假如row.getCell(0)中的数值为12345678910123
                        cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
                        String address = sheet.getRow(i).getCell(4).getStringCellValue();
                        address = address.replace("地址","");
                        DistResult dr = calcDist(address,line);
                        System.out.println("测试距离"+dr.getLat()+","+address);
                        if(dr.getLat()>0) {
//                            li.setDistance(dr.getDist());
                            if(dr.getDist()<=thres) {
//                                li.setInside(1);
                            }else if(dr.getDist()==1000) {
//                                li.setInside(2);
                            }else {
//                                li.setInside(0);
                            }
                            li.setAddress(address);
                            li.setLine(line);
                            li.setBirth("" + sheet.getRow(i).getCell(5).getStringCellValue());
                            li.setCode("" + sheet.getRow(i).getCell(6).getStringCellValue());
                            li.setLat(dr.getLat());
                            li.setLng(dr.getLng());
                            li.setName(sheet.getRow(i).getCell(1).getStringCellValue());
                            li.setSex(sheet.getRow(i).getCell(2).getStringCellValue());
                            li.setNation(sheet.getRow(i).getCell(3).getStringCellValue());
                            // 此处与/user/add接口中的方式一致
                            com.grid.dal.domain.LineInspector example = new com.grid.dal.domain.LineInspector();
                            example.setLat(new BigDecimal(li.getLat()));
                            example.setLng(new BigDecimal(li.getLng()));
                            example.setName(li.getName());
                            example.setBirth(li.getBirth());
                            example.setNation(li.getNation());
                            example.setSex(li.getSex());
                            example.setAddress(li.getAddress());
                            example.setCode(li.getCode());

                            System.out.println(example.getCode()+","+example.getLat()+","+example.getLng());
                            com.grid.dal.domain.LineInspector inspector = userDao.exists(example);
                            // 添加关联记录
                            LineUsers lineUsers = new LineUsers();
                            // 查询线路长度
                            Double len = lins.linelen(line);
//                            System.out.println("--------");
//                            System.out.println(len);
                            System.out.println("用户到线路距离:"+dr.getDist()/1000.0);
                            System.out.println("线路查询长度为:"+len);
                            lineUsers.setDistance(new BigDecimal(dr.getDist()/1000+len*2+1));
                            if(dr.getDist()<=thres) {
                                lineUsers.setInside((short)1);
//                                li.setInside(1);
                            }else if(dr.getDist()==1000) {
//                                li.setInside(2);
                                lineUsers.setInside((short)2);
                            }else {
                                lineUsers.setInside((short)0);
                            }
                            lineUsers.setInsname(inspector.getName());
                            Long lil = Long.valueOf(line);
                            lineUsers.setLine(lil);
                            lineUsers.setInspector(inspector.getId());
                            int resu = userDao.AddUserLine(lineUsers);
//                            lins.delUser(li.getCode());
//                            lins.AddUser(li);
                        }else {
                            if(sheet.getRow(i).getCell(4).getStringCellValue().contains("甘肃省")) {
                                failList.add(sheet.getRow(i).getCell(1).getStringCellValue());
                            }
                        }

                    }else {
                        result.put("result", -1);
                        result.put("errmsg", "格式错误");
                    }
                }
            }
        }
        result.put("result", 0);
        result.put("fail", failList);
        return result;
    }


    @RequestMapping("/shanchu")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object del(String code) {
        this.lins.delUser(code);
        return "";
    }

//    @RequestMapping("/testsql")
//    @ResponseBody
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public Object testSql() {
//        return lins.getCache();
//    }


}
