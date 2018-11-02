package com.grid.service;

import com.grid.Entity.LineEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service("lineService")
public class LineServiceImpl implements LineService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<LineEntity> QueryAll() {
        String sql = "SELECT * FROM T_TX_ZWYC_XL";
        List<LineEntity> list = jdbcTemplate.query(sql, new RowMapper<LineEntity>() {
            //映射每行数据
            @Override
            public LineEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                LineEntity cc = new LineEntity();
                cc.setName(rs.getString("SBMC"));
                cc.setId(rs.getString("OID"));
                return cc;
            }

        });

        return list;
    }
}
