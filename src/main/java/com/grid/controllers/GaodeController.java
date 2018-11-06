package com.grid.controllers;

import com.grid.service.LineService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.grid.utils.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class GaodeController {
    @Autowired
    private LineService lins;





}
