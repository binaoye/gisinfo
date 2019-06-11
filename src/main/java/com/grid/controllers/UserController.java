package com.grid.controllers;


import com.grid.dal.bo.req.AddUserDO;
import com.grid.dal.domain.LineInspector;
import com.grid.dal.domain.LineUsers;
import com.grid.dao.UserDao;
import com.grid.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LineService service;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object adduser(@RequestBody AddUserDO req) {
        System.out.println("11111");
        Map<String, Object> result = new HashMap<String, Object>();
        /*
        先判断用户是否已存在， 如果存在添加关联记录，否则先添加人员再添加关联记录
         */
        LineInspector example = new LineInspector();
        example.setCode(req.getCode());
        example.setLat(new BigDecimal(req.getLat()));
        example.setLng(new BigDecimal(req.getLng()));
        example.setName(req.getName());
        example.setBirth(req.getBirth());
        example.setNation(req.getNation());
        example.setSex(req.getSex());
        example.setAddress(req.getAddress());
        System.out.println(example.getCode());
        LineInspector inspector = userDao.exists(example);
        // 添加关联记录
        LineUsers lineUsers = new LineUsers();
        lineUsers.setDistance(new BigDecimal(req.getDistance()));
        lineUsers.setInside(req.getInside());
        lineUsers.setInsname(inspector.getName());
        lineUsers.setLine(req.getLine());
        lineUsers.setInspector(inspector.getId());
        int resu = userDao.AddUserLine(lineUsers);
        if(resu < 1) {
            result.put("rc", "-1");
        }
        result.put("rc", 0);

        return result;
    }



    /**
     * 下载用户信息
     * @param users
     * @return
     */
    @RequestMapping("/downuser")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object DownUser(String users,HttpServletResponse res) {
        Map<String, Object> result = new HashMap<String, Object>();
//        List<String> us = new ArrayList<String>();
        String[] us = users.split("_");
//        List<LineInspector> lst = service.UserDownload(us);
        List<LineInspector> lst = userDao.listUsers(us);
//        result.put("users", lst);
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + "巡线员.xls");
        try {
            OutputStream os = null;
            BufferedInputStream bis = null;
            os = res.getOutputStream();
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            //创建新的一页
            userExcel(lst, workbook,false);
            //把创建的内容写入到输出流中，并关闭输出流
            workbook.write();
            workbook.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 下载用户excel
     * @param users
     * @param workbook
     * @throws Exception
     */
    private void userExcel(List<LineInspector> users, WritableWorkbook workbook, boolean flag) throws Exception {
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        Label name = new Label(0,0,"姓名");
        sheet.addCell(name);
        Label birth = new Label(1,0,"出生日期");
        sheet.addCell(birth);
        Label sex = new Label(2,0,"性别");
        sheet.addCell(sex);
        Label nation = new Label(3,0,"民族");
        sheet.addCell(nation);
        Label address = new Label(4,0,"家庭住址");
        sheet.addCell(address);
        Label code = new Label(5,0,"身份证号");
        sheet.addCell(code);
        Label distance = new Label(6,0,"线路距离");
        sheet.addCell(distance);
        Label isyes = new Label(7,0,"是否范围内");
        sheet.addCell(isyes);
        if(flag) {
            Label line = new Label(8,0,"线路名称");
            sheet.addCell(line);
            Label dept = new Label(9,0,"所属单位");
            sheet.addCell(dept);
        }


        // 循环写入
        for(int i=0;i< users.size(); i++) {
            Label aname = new Label(0,i+1,users.get(i).getName());
            sheet.addCell(aname);
            Label abirth = new Label(1,i+1,users.get(i).getBirth());
            sheet.addCell(abirth);
            Label asex = new Label(2,i+1,users.get(i).getSex());
            sheet.addCell(asex);
            Label anation = new Label(3,i+1,users.get(i).getNation());
            sheet.addCell(anation);
            Label aaddress = new Label(4,i+1,users.get(i).getAddress());
            sheet.addCell(aaddress);
            Label acode = new Label(5,i+1,users.get(i).getCode());
            sheet.addCell(acode);
//            Label adistance = new Label(6,i+1,String.valueOf(users.get(i).getDistance()));
//            sheet.addCell(adistance);
//            if (users.get(i).getInside() == 1) {
//                Label aisyes = new Label(7,i+1,"是");
//                sheet.addCell(aisyes);
//            }else if(users.get(i).getInside()==0){
//                Label aisyes = new Label(7,i+1,"否");
//                sheet.addCell(aisyes);
//            }else {
//                Label aisyes = new Label(7,i+1,"地址格式错误");
//                sheet.addCell(aisyes);
//            }
//            if(flag) {
//                Label line = new Label(8,i+1,String.valueOf(users.get(i).getLinename()));
//                sheet.addCell(line);
//                Label dept = new Label(9,i+1,String.valueOf(users.get(i).getDept()));
//                sheet.addCell(dept);
//            }


        }
    }
//
//    @RequestMapping("/deleteusers")
//    @ResponseBody
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public Object deleteUsers(String users) {
//        Map<String, Object> result = new HashMap<String, Object>();
////        List<String> us = new ArrayList<String>();
//        String[] us = users.split("_");
//        service.deleteUsers(us);
//        result.put("result", "0");
//        return result;
//    }
//
//    @RequestMapping("/cityusers")
//    @ResponseBody
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public Object cityUsers(String city,HttpServletResponse res) {
//        Map<String, Object> result = new HashMap<String, Object>();
////        List<String> us = new ArrayList<String>();
//        List<LineInspector> inspectors = service.QueryCityInspectors(city);
//        res.setHeader("content-type", "application/octet-stream");
//        res.setContentType("application/octet-stream");
//        res.setHeader("Content-Disposition", "attachment;filename=" + "巡线员.xls");
//        result.put("result", "0");
//        try {
//            OutputStream os = null;
//            BufferedInputStream bis = null;
//            os = res.getOutputStream();
//            WritableWorkbook workbook = Workbook.createWorkbook(os);
//            //创建新的一页
//            userExcel(inspectors, workbook,true);
//            //把创建的内容写入到输出流中，并关闭输出流
//            workbook.write();
//            workbook.close();
//            os.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    @RequestMapping("/allusers")
//    @ResponseBody
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public Object allUsers(HttpServletResponse res) {
//        Map<String, Object> result = new HashMap<String, Object>();
////        List<String> us = new ArrayList<String>();
//        List<LineInspector> inspectors = service.QueryAllInspectors();
//        res.setHeader("content-type", "application/octet-stream");
//        res.setContentType("application/octet-stream");
//        res.setHeader("Content-Disposition", "attachment;filename=" + "全省巡线员.xls");
//        result.put("result", "0");
//        try {
//            OutputStream os = null;
//            BufferedInputStream bis = null;
//            os = res.getOutputStream();
//            WritableWorkbook workbook = Workbook.createWorkbook(os);
//            //创建新的一页
//            userExcel(inspectors, workbook,true);
//            //把创建的内容写入到输出流中，并关闭输出流
//            workbook.write();
//            workbook.close();
//            os.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }







}
