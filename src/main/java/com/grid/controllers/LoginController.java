package com.grid.controllers;

import com.grid.ocrtool.IDCardOcr;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;


@RestController
public class LoginController {
    private IDCardOcr ocr = new IDCardOcr();
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

    @RequestMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            System.out.println("收到文件"+ file.getName());
            try {
                /*
                 * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到
                 * d:/files大家是否能实现呢？ 等等;
                 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；
                 * 3、文件格式; 4、文件大小的限制;
                 */
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(
                                file.getOriginalFilename())));
                System.out.println(file.getName());
                out.write(file.getBytes());
                File f = null;
                f=File.createTempFile("tmp", null);
                file.transferTo(f);
                ocr.recongnize(f);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }

            return "上传成功";

        } else {
            return "上传失败，因为文件是空的.";
        }
    }
}
