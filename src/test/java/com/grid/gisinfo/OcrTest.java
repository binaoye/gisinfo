package com.grid.gisinfo;

import com.grid.ocrtool.IDCardOcr;
import org.junit.Test;
public class OcrTest {

    @Test
    public void ocrTest() {
        String[] path = {"src/main/resources/testdata/test.jpg"};
        IDCardOcr ocr = new IDCardOcr(path);
        ocr.recongnize();
        System.out.print("测试成功");

    }

}
