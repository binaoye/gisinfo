package com.grid.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class TestController {
    private Connection con ;
    private String user = "GISAUDIT" ;
    private String password = "GSgis!123" ;
    private String className = "oracle.jdbc.driver.OracleDriver" ;
    private String url = "jdbc:oracle:thin:@21.76.122.5:1521:qywdb1";

//    private String user = "xiaoke" ;
//    private String password = "298016" ;
//    private String className = "oracle.jdbc.driver.OracleDriver" ;
//    private String url = "jdbc:oracle:thin:@118.190.207.66:1521:orcl" ;
    @RequestMapping("/testOracle")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Object TestOracle() {
        //建立连接

        System.out.println("尝试thin建立连接");
        con = getCon();
//        closed();
        ConnectOracle();
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("user", user);
        result.put("pass", password);
        result.put("url", url);
        return result;
    }
    /** 注册驱动 */
    public void ConnectOracle() {
        try {
            Class. forName(className);
            System. out.println("加载数据库驱动成功！" );
        } catch (ClassNotFoundException e ) {
            System. out.println("加载数据库驱动失败！" );
            e.printStackTrace();
        }
    }

    /** 创建数据库连接 */
    public Connection getCon() {
        try {

            con = DriverManager. getConnection(url, user, password);
            System. out.println("创建数据库连接成功！" );
        } catch (SQLException e ) {
            System. out.print(con );
            System. out.println("创建数据库连接失败！" );
            con = null;
            e.printStackTrace();
        }
        return con ;
    }

    public void closed() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e ) {
            System. out.println("关闭con对象失败！" );
            e.printStackTrace();
        }
    }

}
