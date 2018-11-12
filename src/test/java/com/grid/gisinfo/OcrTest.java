package com.grid.gisinfo;

import org.junit.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OcrTest {

    @Test
    public void ocrTest() {
//        String[] path = {"src/main/resources/testdata/520658465.jpg"};
//        String[] path = {"/home/xiaoke/Pictures/test.png"};
//        IDCardOcr ocr = new IDCardOcr(path);
//        ocr.recongnize();
//        System.out.print("测试成功");
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(11, "加");
        map.put(12, "关");
        map.put(13, "注");
        map.put(10, "添");
        map.put(1, "小");
        map.put(8, "记");
        map.put(9, "得");
        map.put(7, "客");

        Map<Integer, String> sortMap = new TreeMap<Integer, String>(new MapKeyComparator());
        sortMap.putAll(map);

        for (Map.Entry<Integer, String> entry : sortMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }



    }





}
class MapKeyComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer s1, Integer s2) {
        return s1.compareTo(s2);  //从小到大排序
    }
}
