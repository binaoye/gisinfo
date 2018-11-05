package com.grid.controllers;

import com.grid.Entity.LineEntity;
import com.grid.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class MapController {
    @Autowired
    private LineService lins;

    @RequestMapping("/testdist")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object TestDist(String posi) {
        Random rand=new Random();
        Map<String,Integer> result = new HashMap<String, Integer>();
        result.put("result", rand.nextInt(2));
        return result;
    }

    /**
     * 查询市所有线路列表
     * @param city
     * @return
     */
    @RequestMapping("/getlines")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object QueryLines(String city) {
        System.out.println("查询城市"+city);
        List<LineEntity> lines = lins.QueryAll(city);
        return lines;
    }

    /**
     * 查询市级单位列表
     * @return
     */
    @RequestMapping("/getCities")
    @ResponseBody
    public Object QueryCities() {
        return this.lins.QueryCity();
    }

    /**
     * 查询线路所有点
     * @param line
     * @return
     */
    @RequestMapping("/getLinePoints")
    @ResponseBody
    public Object QueryLine(String line) {
        return this.lins.QueryLinePoint(line);
    }

    @RequestMapping("/queryCityLinePoints")
    @ResponseBody
    public Object QueryCityLinePoints(String city) {
        return this.lins.QueryCityLinePoints(city);
    }
}
