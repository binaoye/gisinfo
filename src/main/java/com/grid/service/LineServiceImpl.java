package com.grid.service;

import com.grid.Entity.CityEntity;
import com.grid.Entity.LineEntity;
import com.grid.dao.CItyDao;
import com.grid.dao.LineDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service("lineService")
public class LineServiceImpl implements LineService {
    @Resource
    private LineDao ldao;
    @Resource
    private CItyDao cdao;
    @Override
    public List<LineEntity> QueryAll(String city) {
        return this.ldao.QueryLines(city);
    }

    public List<CityEntity> QueryCity() {
        return this.cdao.Query();
    }

    @Override
    public Map<String, double[][]> QueryLinePoint(String line) {
        return this.ldao.QueryLinePoints(line);
    }

    @Override
    public Map<String, double[][]> QueryCityLinePoints(String city) {
        return this.ldao.QueryCityLinePoints(city);
    }
}
