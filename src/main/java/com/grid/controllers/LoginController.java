package com.grid.controllers;

import com.grid.ocrtool.IDCardInfo;
import com.grid.ocrtool.IDCardOcr;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {
    private IDCardOcr ocrcc = new IDCardOcr();
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @RequestMapping("/hello")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public String hello() {
        String prepath = System.getProperty("os.name");
        if (prepath.toLowerCase().startsWith("win")) {
            String[] path = {"c:\\test.jpg"};
            IDCardOcr ocr = new IDCardOcr(path);
            ocr.recongnize();
            System.out.print("测试成功");
        }else {
//            String[] path = {"src/main/resources/testdata/test.jpg"};
            String[] path = {"/home/xiaoke/IdeaProjects/gisinfo/src/main/resources/testdata/test.jpg"};
            IDCardOcr ocr = new IDCardOcr(path);
            ocr.recongnize();
            System.out.print("测试成功");
        }
        return "Hello World";
    }

    @RequestMapping("/upload")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object handleFileUpload(@RequestParam("file") MultipartFile file) {
        IDCardInfo info = new IDCardInfo();
        IDCardOcr ocr = new IDCardOcr();
        if (!file.isEmpty()) {
            System.out.println("收到文件"+ file.getName());

            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(
                                file.getOriginalFilename())));
                System.out.println("传入文件名"+ file.getOriginalFilename());
                out.write(file.getBytes());
                File f = null;
                f=File.createTempFile("tmp", null);
                file.transferTo(f);
                info = ocr.recongnize(f);
                info.setFilename(file.getOriginalFilename());
                System.out.println("识别到结果");
                System.out.printf("姓名：%s\n", info.getName());
                System.out.printf("性别：%s\n", info.getSex());
                System.out.printf("生日：%s\n", info.getBirth());
                System.out.printf("民族：%s\n", info.getNation());
                System.out.printf("住址：%S\n", info.getLocation());
                System.out.printf("号码：%s\n", info.getNumber());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                info = new IDCardInfo();
                info.setErrmsg("图片不存在");
                return info;
            } catch (IOException e) {
                info = new IDCardInfo();
                info.setErrmsg("上传失败");
                return info;
            }

            return info;

        } else {
            info = new IDCardInfo();
            info.setErrmsg("图片不存在");
            return info;
        }
    }

    @RequestMapping("/uptest")
    @ResponseBody
    @CrossOrigin
    public Object handleTest() {
        IDCardInfo info = new IDCardInfo();
        info.setErrmsg("测试成功");
        return info;
    }

}
