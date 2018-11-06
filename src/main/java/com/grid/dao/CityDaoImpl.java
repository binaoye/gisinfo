package com.grid.dao;

import com.grid.Entity.CityEntity;
import com.grid.Entity.GeoCache;
import com.grid.Entity.LineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CityDaoImpl implements CItyDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<CityEntity> Query() {
        String sql = "SELECT * FROM city";
        List<CityEntity> list = jdbcTemplate.query(sql, new RowMapper<CityEntity>() {
            //映射每行数据
            @Override
            public CityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CityEntity cc = new CityEntity();
                System.out.println(rs.first());
                cc.setID(rs.getString("ssds"));
                cc.setName(rs.getString("name"));
                cc.setCenter(rs.getString("center"));
                return cc;
            }
        });
        return list;
    }

    @Override
    public GeoCache GeoEncode(String city, String county, String street, String village) {
        // 先拼接基础
        String sql = "SELECT * FROM gps_cache WHERE village='%s' AND county='%s' AND street='%S'" ;
        String s = String.format(sql,village,county,street);
        System.out.println(s);
        GeoCache result = new GeoCache();
        List<GeoCache> list = jdbcTemplate.query(s, new RowMapper<GeoCache>() {
            //映射每行数据
            @Override
            public GeoCache mapRow(ResultSet rs, int rowNum) throws SQLException {
                GeoCache cc = new GeoCache();
                cc.setLatitude(rs.getDouble("lat_float"));
                cc.setLongitude(rs.getDouble("lng_float"));
//                cc.setID(rs.getString("ssds"));
//                cc.setName(rs.getString("name"));
//                cc.setCenter(rs.getString("center"));
                return cc;
            }
        });
        if(list.size() > 0) {
            result = list.get(0);
        }
        return result;
    }
}
