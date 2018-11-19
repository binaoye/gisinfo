package com.grid.dao;

import com.grid.Entity.CityEntity;
import com.grid.Entity.GeoCache;
import com.grid.Entity.LineEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CityDaoImpl implements CItyDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    List citys = new ArrayList();
    @Override
    public List<CityEntity> Query() {
        String sql = "SELECT * FROM city";
        List<CityEntity> list = jdbcTemplate.query(sql, new RowMapper<CityEntity>() {
            //映射每行数据
            @Override
            public CityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CityEntity cc = new CityEntity();
//                System.out.println(rs.first());
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

    @Override
    public List<String> GetCities() {
//        if (citys.size() > 1 ){
//            return citys;
//        }
        String sql = "SELECT DISTINCT(city) FROM gps_cache";
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        citys = list;
        return list;
    }

    @Override
    public List<String> GetCounties(String city) {
        String sqlFormat = "SELECT DISTINCT(county) FROM gps_cache WHERE city=%s";
        String sql_county = String.format(sqlFormat, city);
        System.out.println("sql:" + sql_county);
        List<String> county_list = jdbcTemplate.queryForList(sql_county, String.class);
        return county_list;
    }

    @Override
    public List<String> GetStreets(String city, String county) {
        String sqlFormat = "SELECT DISTINCT(street) FROM gps_cache WHERE city='%s' AND county='%s'";
        String sql = String.format(sqlFormat, city, county);
        System.out.println("sql：" + sql);
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<String> GetVillages(String city, String county, String street) {
        String sqlFormat = "SELECT DISTINCT(village) FROM gps_cache WHERE city='%s' AND county='%s' AND street='%s'";
        String sql = String.format(sqlFormat, city, county, street);
        System.out.println("sql：" + sql);
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<CityEntity> GetProvDept() {
        String sql = "SELECT dwbm, dwmc FROM t_v_isc_dept where sjdwbm='008df5db70319f73e0508eoabd9b0002' group by dwbm,dwmc";
        List<CityEntity> list = jdbcTemplate.query(sql, new RowMapper<CityEntity>() {
            //映射每行数据
            @Override
            public CityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CityEntity cc = new CityEntity();
//                System.out.println(rs.first());
                cc.setID(rs.getString("dwbm"));
                cc.setName(rs.getString("dwmc"));
//                cc.setCenter(rs.getString("center"));
                return cc;
            }
        });
        return list;
        }
}
