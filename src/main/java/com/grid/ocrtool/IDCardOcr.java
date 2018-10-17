package com.grid.ocrtool;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.context.annotation.Bean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IDCardOcr {
    private ITesseract instance;
    private String[] inputs;
    private IDCardInfo[] outputs;
    private float[] confidence;
    private Map<String, cutZone> zones;
    private String prepath;
    public IDCardOcr(String[] inputs) {
        this.init();
        this.inputs = inputs;
    }

    private void init() {
        this.instance = new Tesseract();
        this.instance.setLanguage("chi_sim");
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            this.prepath = "C:\\Users\\Administrator\\Desktop";
            this.instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
        } else {
            this.prepath = "/home/xiaoke/terhome/";
            this.instance.setDatapath("/usr/share/tesseract-ocr/tessdata");
        }
        //生成切割识别区域
        genCutZones();
    }

    public IDCardOcr() {
        this.init();
    }

    void genCutZones() {
        Map<String, cutZone> zones = new HashMap<String, cutZone>();
        // 号码区域
        cutZone number = new cutZone(0.25, 0.75, 0.2, 0.65);
        zones.put("number", number);
        // 住址区域
        cutZone address = new cutZone(0.05, 0.48, 0.22, 0.65);
        zones.put("address", address);
        // 生日区域
        cutZone birth = new cutZone(0.05, 0.36, 0.12, 0.65);
        zones.put("birth", birth);
        // 性别区域
        cutZone sex = new cutZone(0.05, 0.20, 0.12, 0.65);
        zones.put("sex", sex);
        // 姓名区域
        cutZone name = new cutZone(0.05, 0.10, 0.13, 0.5);
        zones.put("name", name);

        this.zones = zones;
    }

    public void recongnize() {
        for(String path: this.inputs) {
            System.out.print(IDCardOcr.class.getName() + "开始识别：" + path);
            // 截取各个部分
            Map<String, BufferedImage> parts = getLocates(path);
            readParts(parts);
        }
    }

    public IDCardInfo readParts(Map<String, BufferedImage> parts) {
        System.out.println("开始识别切割图像");
        IDCardInfo info = new IDCardInfo();
        for(Map.Entry<String, BufferedImage> entry: parts.entrySet()) {
            try {
                System.out.println("测试ocr" + entry.getKey());
                String result = this.instance.doOCR(entry.getValue());
                System.out.println("doocr成功");
                // 处理result

                String savePath = this.prepath + "/" + entry.getKey() + ".jpg";
                ImageIO.write(entry.getValue(), "jpg", new File(savePath) );
                System.out.println("存储路径变成"+ savePath);
                System.out.print("当前部分为" + entry.getKey() + "识别结果为：" + result);
            } catch (Exception e) {
                System.out.println("当前部分为" + entry.getKey() + "识别失败");
                e.printStackTrace();
            }
        }
        return info;
    }

//    private IDCardInfo processPart(IDCardInfo info, String result, String key) {
//        switch (key) {
//            case "number":
//                // 从左至右开始遍历, 如果是数字或x继续, 否则结束
//
//
//        }
//    }

    public IDCardInfo recongnize(File file) {
        Map<String, BufferedImage> parts = getLocates(file);
        IDCardInfo info = readParts(parts);
        return info;
    }

    private BufferedImage cut(cutZone zone, BufferedImage image) {
        int sHeight = image.getHeight();
        int sWidth = image.getWidth();
        int newHeight = (int) (sHeight * zone.getHeight());
        int newWidth = (int) (sWidth * zone.getWidth());
        int[] simgRgb = new int[newWidth * newHeight];
        int x = (int) (sWidth * zone.getX());
        int y = (int) (sHeight * zone.getY());
        System.out.printf("尝试切割图像: %d, %d, %d, %d, %d, %d\n",sHeight, sWidth,x, y, newHeight, newWidth);
        BufferedImage newImage = image.getSubimage(x, y, newWidth,newHeight);
        return newImage;
    }

    private Map<String, BufferedImage> getLocates(File file) {
        Map<String, BufferedImage> result = new HashMap<String, BufferedImage>();
        try {
            BufferedImage textImage = ImageIO.read(file);
            for(Map.Entry<String, cutZone> entry : this.zones.entrySet()) {
                BufferedImage img = cut(entry.getValue(), textImage);
                result.put(entry.getKey(), img);
            }
        } catch (IOException e) {
            System.out.println("读取切割文件失败");
            e.printStackTrace();
        }
        return result;
    }



    private Map<String, BufferedImage> getLocates(String path) {
//        Map<String, BufferedImage> result = new HashMap<String, BufferedImage>();
        // 读取待切割的图像
        File file = new File(path);
        Map<String, BufferedImage> result = getLocates(file);
        return result;
    }

    public String deluseless(String str) {
        String[] delwords = {
                ",", "|", "/", "_", "-", "+", "^", "#", "!", "~", "$", "%", "*",
                "(", ")", "[", "]", "{", "}", ";", ":", "'", "\"","<", ">", "?"
        };
        for(String st: delwords) {
            str = str.replace(st, "");
        }
        return str;
    }



}
class cutZone{
    private Double x;
    private Double y;
    private Double height;
    private Double width;

    public cutZone(Double x, Double y, Double height, Double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }
}

