package com.grid.dao;

import com.grid.Entity.CityEntity;
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
}
