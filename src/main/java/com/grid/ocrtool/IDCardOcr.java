package com.grid.ocrtool;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

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

    public IDCardOcr(String[] inputs) {
        this.instance = new Tesseract();
        this.instance.setLanguage("chi_sim");
        this.instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
        this.inputs = inputs;
        //生成切割识别区域
        genCutZones();
    }

    void genCutZones() {
        Map<String, cutZone> zones = new HashMap<String, cutZone>();
        // 号码区域
        cutZone number = new cutZone(0.0, 0.8, 0.2, 1.0);
        zones.put("number", number);
        // 住址区域
        cutZone address = new cutZone(0.0, 0.48, 0.22, 0.65);
        zones.put("address", address);
        // 生日区域
        cutZone birth = new cutZone(0.0, 0.36, 0.12, 0.65);
        zones.put("birth", birth);
        // 性别区域
        cutZone sex = new cutZone(0.0, 0.24, 0.12, 0.65);
        zones.put("sex", sex);
        // 姓名区域
        cutZone name = new cutZone(0.0, 0.10, 0.13, 0.5);
        zones.put("name", name);

        this.zones = zones;
    }

    public void recongnize() {
        for(String path: this.inputs) {
            System.out.print(IDCardOcr.class.getName() + "开始识别：" + path);
            // 截取各个部分
            Map<String, BufferedImage> parts = getLocates(path);
            for(Map.Entry<String, BufferedImage> entry: parts.entrySet()) {
                try {
                    String result = this.instance.doOCR(entry.getValue());
                    ImageIO.write(entry.getValue(), "jpg", new File("F:\\tess\\" + entry.getKey() + ".jpg") );
                    System.out.print("当前部分为" + entry.getKey() + "识别结果为：" + result);
                } catch (Exception e) {
                    System.out.println("当前部分为" + entry.getKey() + "识别失败");
                    e.printStackTrace();
                }
            }

        }
    }

    private BufferedImage cut(cutZone zone, BufferedImage image) {
        int sHeight = image.getHeight();
        int sWidth = image.getWidth();
        int newHeight = (int) (sHeight * zone.getHeight());
        int newWidth = (int) (sWidth * zone.getWidth());
        int[] simgRgb = new int[newWidth * newHeight];
        int x = (int) (sWidth * zone.getX());
        int y = (int) (sHeight * zone.getY());
        System.out.printf("尝试切割图像: %d, %d, %d, %d, %d, %d",sHeight, sWidth,x, y, newHeight, newWidth);
        BufferedImage newImage = image.getSubimage(x, y, newWidth,newHeight);
//        image.getRGB(x, y, newWidth, newHeight, simgRgb, 0, newWidth);
//        BufferedImage newImage = new BufferedImage(newWidth, newHeight,
//                BufferedImage.TYPE_INT_ARGB);
//        newImage.setRGB(0, 0, newWidth, newHeight, simgRgb, 0, newWidth);
        return newImage;
    }

    private Map<String, BufferedImage> getLocates(String path) {
        Map<String, BufferedImage> result = new HashMap<String, BufferedImage>();
        // 读取待切割的图像
        File file = new File(path);
        try {
            BufferedImage textImage = ImageIO.read(file);
            for(Map.Entry<String, cutZone> entry : this.zones.entrySet()) {
                BufferedImage img = cut(entry.getValue(), textImage);
                result.put(entry.getKey(), img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
