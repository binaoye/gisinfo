package com.grid.controllers;

import com.grid.ocrtool.IDCardOcr;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @RequestMapping("/hello")
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

    @RequestMapping("/shit")
    public String Shit() {
        return "shit";
    }
}
