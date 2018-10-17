package com.grid.gisinfo;

import com.grid.ocrtool.IDCardOcr;
import org.junit.Test;
public class OcrTest {

    @Test
    public void ocrTest() {
//        String[] path = {"src/main/resources/testdata/520658465.jpg"};
        String[] path = {"/home/xiaoke/Pictures/test.png"};
        IDCardOcr ocr = new IDCardOcr(path);
        ocr.recongnize();
        System.out.print("测试成功");

    }

}
