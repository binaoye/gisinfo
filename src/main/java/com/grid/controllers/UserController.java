package com.grid.controllers;


import com.grid.Entity.LineInspector;
import com.grid.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    private LineService service;

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
        List<LineInspector> lst = service.UserDownload(us);
//        result.put("users", lst);
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + "xunxianyuan.xls");
        try {
            OutputStream os = null;
            BufferedInputStream bis = null;
            os = res.getOutputStream();
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            //创建新的一页
            userExcel(lst, workbook);
            //把创建的内容写入到输出流中，并关闭输出流
            workbook.write();
            workbook.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private void userExcel(List<LineInspector> users, WritableWorkbook workbook) throws Exception {
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
            Label adistance = new Label(6,i+1,String.valueOf(users.get(i).getDistance()));
            sheet.addCell(adistance);
            if (users.get(i).getInside() == 1) {
                Label aisyes = new Label(7,i+1,"是");
                sheet.addCell(aisyes);
            }else {
                Label aisyes = new Label(7,i+1,"否");
                sheet.addCell(aisyes);
            }

        }
    }




}
