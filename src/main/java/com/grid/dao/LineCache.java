package com.grid.dao;

import com.grid.Entity.CityEntity;
import com.grid.Entity.LinePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LineCache {
    private String sss;
    private Map<String, Map<String, double[][]>> mcache;
    private JdbcTemplate jdbcTemplate;
    public Map<String, double[][]> getCity(String city) {
        if (mcache.containsKey(city)) {
            return mcache.get(city);
        }
        return null;
    }


    public LineCache(JdbcTemplate jdbcTemplate) {
        // 查询市列表
        this.jdbcTemplate = jdbcTemplate;
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
        for(CityEntity ct:list) {
            Map<String, double[][]> mmp = que(ct.getID());
            System.out.println("调用初始化状态"+ct.getName());
            mcache.put(ct.getName(),mmp);
        }

    }

    public Map<String, double[][]> que(String city) {
        //查询线路上所有点
        String sql = " select a.GTPLXH ,b.* from t_sb_zwyc_gt a join t_tx_zwyc_yxgt b on OBJ_ID=SBID where a.SSDS='" + city+"'";
        List<LinePoint> list = jdbcTemplate.query(sql, new RowMapper<LinePoint>() {
            //映射每行数据
            @Override
            public LinePoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                LinePoint cc = new LinePoint();
                cc.setLat(rs.getDouble("SHAPE.SDO_POINT.X"));
                cc.setLng(rs.getDouble("SHAPE.SDO_POINT.Y"));
                cc.setSsxl(rs.getString("SSXL"));
                cc.setGTPLXH(rs.getInt("GTPLXH"));
                return cc;
            }
        });
        // 预处理所有结果
        Map<String,Map<Integer,double[]>> pre = new HashMap<String,Map<Integer,double[]>>();
        for(LinePoint point:list) {
            // 如果已存在ssxl，则取出值
            Map<Integer,double[]> mmp = new HashMap<Integer,double[]>();
            if (pre.containsKey(point.getSsxl())) {
                mmp = pre.get(point.getSsxl());

            }
            double[] ns = new double[2];
            ns[0] = point.getLat();
            ns[1] = point.getLng();
            mmp.put(point.getGTPLXH(),ns);
            pre.put(point.getSsxl(), mmp);
        }
        // 排序

        Map<String,double[][]> result = new HashMap<String,double[][]>();
        //遍历结果集
        for(String k:pre.keySet()) {
            Map<Integer,double[]> nmp = pre.get(k);
            double[][] res = new double[nmp.keySet().size()][];
            Map<Integer, double[]> sortMap = new TreeMap<Integer, double[]>(new MapKeyComparator());
            sortMap.putAll(nmp);
            int i = 0;
            for (Map.Entry<Integer, double[]> entry : sortMap.entrySet()) {
                //放入结果集
                res[i] = entry.getValue();
                i = i + 1;
                System.out.println(entry.getKey() + " " + res);
            }
            result.put(k, res);
        }
        return result;
    }
}
