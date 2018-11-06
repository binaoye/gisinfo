package com.grid.dao;

import com.grid.Entity.LineEntity;
import com.grid.Entity.LinePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class LineDaoImpl implements LineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    private LineCache cache = new LineCache(jdbcTemplate);

    @Override
    public List<LineEntity> QueryLines(String city) {
        String sql = "SELECT * FROM T_TX_ZWYC_XL where ssds='" + city + "'";
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

    @Override
    public Map<String, double[][]> QueryLinePoints(String line) {
        //查询线路上所有点
        String sql = "select a.GTPLXH,b.* from t_sb_zwyc_gt a join t_tx_zwyc_yxgt b on OBJ_ID=SBID where b.ssxl='" + line + "' order by a.GTPLXH asc";
        List<LinePoint> list = jdbcTemplate.query(sql, new RowMapper<LinePoint>() {
            //映射每行数据
            @Override
            public LinePoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                LinePoint cc = new LinePoint();
                cc.setLat(rs.getDouble("SHAPE.SDO_POINT.X"));
                cc.setLng(rs.getDouble("SHAPE.SDO_POINT.Y"));
//                System.out.println(rs.getString("GTPLXH"));
                return cc;
            }

        });
        // 查询所有支线列表


        // 对于每一个支线，使用GTPLXH字段排序
        Map<String,double[][]> result = new HashMap<String,double[][]>();
//        String[] str = new String[list.size()];
        ArrayList arr = new ArrayList();
        for(LinePoint point: list) {
            double[] nss = new double[2];
            nss[0] = point.getLat();
            nss[1] = point.getLng();
            System.out.println("["+point.getLat()+", "+ point.getLng()+"],");
            arr.add(nss);
        }
        int size=arr.size();
        int m = 0;
        if (size > 2) {
            m = size/2;
        }
        double[][] array = (double[][])arr.toArray(new double[size][]);
        double[][] mid = new double[1][];
        mid[0] = array[m];
        result.put(line, array);
        result.put("center", mid);
        return result;
    }

    @Override
    public Map<String, double[][]> QueryCityLinePoints(String city) {
        return null;
    }


//    @Override
//    public Map<String, double[][]> QueryCityLinePoints(String city) {
//        //查询线路上所有点
//        String sql = " select a.GTPLXH ,b.* from t_sb_zwyc_gt a join t_tx_zwyc_yxgt b on OBJ_ID=SBID where a.SSDS='" + city+"'";
//        List<LinePoint> list = jdbcTemplate.query(sql, new RowMapper<LinePoint>() {
//            //映射每行数据
//            @Override
//            public LinePoint mapRow(ResultSet rs, int rowNum) throws SQLException {
//                LinePoint cc = new LinePoint();
//                cc.setLat(rs.getDouble("SHAPE.SDO_POINT.X"));
//                cc.setLng(rs.getDouble("SHAPE.SDO_POINT.Y"));
//                cc.setSsxl(rs.getString("SSXL"));
//                cc.setGTPLXH(rs.getInt("GTPLXH"));
//                return cc;
//            }
//        });
//        // 预处理所有结果
//        Map<String,Map<Integer,double[]>> pre = new HashMap<String,Map<Integer,double[]>>();
//        for(LinePoint point:list) {
//            // 如果已存在ssxl，则取出值
//            Map<Integer,double[]> mmp = new HashMap<Integer,double[]>();
//            if (pre.containsKey(point.getSsxl())) {
//                mmp = pre.get(point.getSsxl());
//
//            }
//            double[] ns = new double[2];
//            ns[0] = point.getLat();
//            ns[1] = point.getLng();
//            mmp.put(point.getGTPLXH(),ns);
//            pre.put(point.getSsxl(), mmp);
//        }
//        // 排序
//
//        Map<String,double[][]> result = new HashMap<String,double[][]>();
//        //遍历结果集
//        for(String k:pre.keySet()) {
//            Map<Integer,double[]> nmp = pre.get(k);
//            double[][] res = new double[nmp.keySet().size()][];
//            Map<Integer, double[]> sortMap = new TreeMap<Integer, double[]>(new MapKeyComparator());
//            sortMap.putAll(nmp);
//            int i = 0;
//            for (Map.Entry<Integer, double[]> entry : sortMap.entrySet()) {
//                //放入结果集
//                res[i] = entry.getValue();
//                i = i + 1;
//                System.out.println(entry.getKey() + " " + res);
//            }
//            result.put(k, res);
//        }
//        return result;
//    }
}

class MapKeyComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer s1, Integer s2) {
        return s1.compareTo(s2);  //从小到大排序
    }
}