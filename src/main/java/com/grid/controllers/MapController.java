package com.grid.controllers;

import com.grid.Entity.LineEntity;
import com.grid.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Object TestDist() {
        Random rand=new Random();
        Map<String,Integer> result = new HashMap<String, Integer>();
        result.put("result", rand.nextInt(2));
        return result;
    }

    @RequestMapping("/getlines")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object QueryLines() {
        List<LineEntity> lines = lins.QueryAll();
        return lines;
    }
}
