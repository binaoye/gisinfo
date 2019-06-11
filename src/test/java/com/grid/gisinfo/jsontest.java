package com.grid.gisinfo;


import com.alibaba.fastjson.JSON;
import com.grid.dal.bo.req.AddUserDO;

public class jsontest {
    public static void main(String[] args) {
        AddUserDO inspector = new AddUserDO();
        inspector.setAddress("北京市海淀区");
        inspector.setBirth("19991225");
        inspector.setCode("4465613132113");
        inspector.setDistance(1111.011);
//        inspector.setInside(2);
        inspector.setName("光头强");
        inspector.setNation("汉");
        inspector.setSex("male");
        inspector.setLat(120.111);
        inspector.setLng(130.222);
        System.out.println(JSON.toJSONString(inspector, true));
    }
}
