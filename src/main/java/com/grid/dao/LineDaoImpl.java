package com.grid.dao;

import com.grid.Entity.*;
import oracle.spatial.geometry.JGeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
    public Map<String, Object> QueryLinePoints(String line) {
        //查询线路上所有点
//        String sqlFormat = "select c.*,d.gtcz from t_sb_zwyc_wlg d right join " +
//                "(select a.GTPLXH,a.glwlgt,b.* from t_sb_zwyc_gt a left join t_tx_zwyc_yxgt b on a.obj_id=b.sbid where b.ssxl='%s' order by a.GTPLXH asc) c " +
//                "on c.glwlgt=d.OBJ_ID";
        String sqlFormat = "select c.*,d.gtcz from t_sb_zwyc_wlg d join (select a.GTPLXH,a.glwlgt,b.* from t_sb_zwyc_gt a join t_tx_zwyc_yxgt b on OBJ_ID=SBID where b.ssxl='%s' order by a.GTPLXH asc) c on c.glwlgt=d.OBJ_ID";
        String sql = String.format(sqlFormat, line);
        System.out.println("sql:"+ sql);


        List<LinePoint> list = jdbcTemplate.query(sql, new RowMapper<LinePoint>() {
            //映射每行数据
            @Override
            public LinePoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                LinePoint cc = new LinePoint();
                byte[] image = new byte[0];
                cc.setSSDKXZX(rs.getString("SSDKXZX"));
                image = rs.getBytes("SHAPE");
                try {
                    JGeometry metry = JGeometry.load(image);
                    double[] point = metry.getLabelPointXYZ();
                    if(point.length>=2) {
                        cc.setLat(point[1]);
                        cc.setLng(point[0]);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                cc.setGTCZ(rs.getString("GTCZ"));
//                cc.setDYDJ(rs.getString("DYDJ"));
                return cc;
            }
        });
        System.out.println("查询到的条数"+list.size());

        // 对于每一个支线，使用GTPLXH字段排序
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,ArrayList> temp = new HashMap<String, ArrayList>();
        Map<String,ArrayList> gtczx = new HashMap<String, ArrayList>();
//        String[] str = new String[list.size()];
        ArrayList arr = new ArrayList();
        for(LinePoint point: list) {
            String thisline = line;
            double[] nss = new double[2];
            nss[0] = point.getLat();
            nss[1] = point.getLng();
            arr.add(nss);
            if (point.getSSDKXZX()!=null) {
                thisline = point.getSSDKXZX();
            }
            if(temp.containsKey(thisline)) {
                ArrayList arrs = temp.get(thisline);
                arrs.add(nss);
                temp.put(thisline, arrs);
                //添加材质
                ArrayList gts = gtczx.get(thisline);
                gts.add(point.getGTCZ());
                gtczx.put(thisline, gts);
            }else {
                ArrayList arrs = new ArrayList();
                arrs.add(nss);
                temp.put(thisline, arrs);
                ArrayList gts = new ArrayList();
                gts.add(point.getGTCZ());
                gtczx.put(thisline, gts);
            }

        }
        int size=arr.size();
        int m = 0;
        if (size > 2) {
            m = size/2;
        }
        double[][] array = (double[][])arr.toArray(new double[size][]);
        double[][] mid = new double[1][];
        if (size > 0){
            mid[0] = array[m];
        }

        result.put(line, temp);
        result.put("gtcz", gtczx);
        result.put("center", mid);
        // 查询线路属性分类
//        result.put()
        Map<String, String> xlxzmap = new HashMap<String, String>();
        xlxzmap.put("1","主线");
        xlxzmap.put("2","支线");
        xlxzmap.put("3","分支线");
        xlxzmap.put("4","分段线路");
        xlxzmap.put("5","馈线");
        String sqlFormatxl = "SELECT a.DYDJ,XLXZ FROM t_sb_zwyc_xl a join t_tx_zwyc_xl b on a.OBJ_ID=b.SBID where OID='%s'";
        List<LineFeature> lf = jdbcTemplate.query(String.format(sqlFormatxl, line), new RowMapper<LineFeature>() {
            @Override
            public LineFeature mapRow(ResultSet rs, int i) throws SQLException {
                LineFeature ll = new LineFeature();
                ll.setXLXZ(xlxzmap.get(rs.getString("XLXZ")));
                ll.setDydj(rs.getString("DYDJ"));
                return ll;
            }
        });
        if(lf.size() > 0) {
            result.put("xlxz", lf.get(0).getXLXZ());
            result.put("dydj", lf.get(0).getDydj());
        }else {
            result.put("xlxz", "0");
            result.put("dydj", "0");
        }

        return result;
    }

    @Override
    public Map<String, double[][]> QueryCityLinePoints(String city) {
        return null;
    }

    @Override
    public List<LineInspector> QueryLineInspector(String line) {
        String sqlFormat = "SELECT * FROM line_inspector WHERE line='%s'";
        String sql = String.format(sqlFormat, line);
        List<LineInspector> list = jdbcTemplate.query(sql, new RowMapper<LineInspector>() {
            @Override
            public LineInspector mapRow(ResultSet rs, int i) throws SQLException {
                LineInspector li = new LineInspector(rs.getString("name"),
                        rs.getString("birth"), rs.getString("nation"), rs.getString("sex"),
                        rs.getString("address"), rs.getString("code"), rs.getString("line"),
                        rs.getDouble("lat"), rs.getDouble("lng"),rs.getInt("inside")
                );
                li.setId(rs.getInt("id"));
//                li.setId(0);
                li.setDistance(rs.getDouble("distance"));
                return li;
            }
        });

        return list;
    }

    @Override
    public Integer AddUser(LineInspector user) {
        String sqlFormat = "INSERT INTO line_inspector( name,birth,nation,sex,address,code, line, inside, lat, lng, distance)" +
                " VALUES('%s', '%s', '%s','%s', '%s', '%s', '%s', %d, %f, %f, %f)";
        String sql = String.format(sqlFormat, user.getName(), user.getBirth(), user.getNation(), user.getSex(), user.getAddress(), user.getCode(), user.getLine(), user.getInside(), user.getLat(), user.getLng(), user.getDistance());
        KeyHolder kh = new GeneratedKeyHolder();
        System.out.println(sql);
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, "nid");
//                ps.setInt(1, 0);
//                ps.setLong(0,0);
                return ps;
            }
        }, kh);
        int id = kh.getKey().intValue();
        System.out.println("查询到主键:" + id);

        return id;
    }

    @Override
    public void UpdateUser(LineInspector user) {

    }

    @Override
    public void UserExists(LineInspector user) {

    }

    @Override
    public List<LineInspector> DownUsers(String[] users) {
        String sqlFormat = "SELECT * FROM line_inspector WHERE id in(%s)";
        String ids = String.join(",", users);
        String sql = String.format(sqlFormat, ids);
        System.out.println(sql);
        List<LineInspector> list = jdbcTemplate.query(sql, new RowMapper<LineInspector>() {
            @Override
            public LineInspector mapRow(ResultSet rs, int i) throws SQLException {
                LineInspector li = new LineInspector(rs.getString("name"),
                        rs.getString("birth"), rs.getString("nation"), rs.getString("sex"),
                        rs.getString("address"), rs.getString("code"), rs.getString("line"),
                        rs.getDouble("lat"), rs.getDouble("lng"),rs.getInt("inside")
                );
                li.setId(rs.getInt("id"));
                li.setId(0);
                li.setDistance(rs.getDouble("distance"));
                return li;
            }
        });
        return list;
    }

    @Override
    public Map<String, double[][]> ListLinePoints(String line) {
        //查询线路上所有点
        String sql = "select a.GTPLXH,b.* from t_sb_zwyc_gt a join" +
                " t_tx_zwyc_yxgt b on OBJ_ID=SBID where b.ssxl='" + line + "' order by a.GTPLXH asc";
        System.out.println("sql:"+ sql);
        List<LinePoint> list = jdbcTemplate.query(sql, new RowMapper<LinePoint>() {
            //映射每行数据
            @Override
            public LinePoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                LinePoint cc = new LinePoint();
//                cc.setSSDKXZX(rs.getString("SSDKXZX"));
                byte[] image = new byte[0];
                image = rs.getBytes("SHAPE");
                try {
                    JGeometry metry = JGeometry.load(image);
                    double[] point = metry.getLabelPointXYZ();
                    if(point.length>=2) {
                        cc.setLat(point[1]);
                        cc.setLng(point[0]);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                System.out.println(rs.getString("GTPLXH"));
                return cc;
            }
        });
        // 查询所有支线列表


        // 对于每一个支线，使用GTPLXH字段排序
        Map<String,double[][]> result = new HashMap<String,double[][]>();
        Map<String,ArrayList> temp = new HashMap<String, ArrayList>();
//        String[] str = new String[list.size()];
        ArrayList arr = new ArrayList();
        for(LinePoint point: list) {
            double[] nss = new double[2];
            nss[0] = point.getLat();
            nss[1] = point.getLng();
            arr.add(nss);
//
////            System.out.println("["+point.getLat()+", "+ point.getLng()+"],");
//            arr.add(nss);
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
    public LineFeature QueryLineFeature(String line) {
        String sqlFormat = "SELECT * FROM t_sb_zwyc_xl a join t_tx_zwyc_xl b on a.\uFEFFOBJ_ID=b.SBID where OID='%s'";
        String sql = String.format(sqlFormat, line);
        Map<String, String> xlxzmap = new HashMap<String, String>();
        xlxzmap.put("1","主线");
        xlxzmap.put("2","支线");
        xlxzmap.put("3","分支线");
        xlxzmap.put("4","分段线路");
        xlxzmap.put("5","馈线");
        Map<String, String> yxztmap = new HashMap<String, String>();
        yxztmap.put("31","现场留用");
        yxztmap.put("32","库存备用");
        yxztmap.put("20","在运");
        yxztmap.put("30","退役");
        yxztmap.put("40","报废");
        yxztmap.put("33","待报废");
        yxztmap.put("10","未投运");
        Map<String,String> zcxzmap = new HashMap<String, String>();
        zcxzmap.put("01","国家电网公司");
        zcxzmap.put("02","分部");
        zcxzmap.put("03","省（直辖市、自治区）");
        zcxzmap.put("04","子公司");
        zcxzmap.put("05","用户");
        Map<String,String> sbzjfsmap = new HashMap<String, String>();
        sbzjfsmap.put("01","设备增加-基本建设");
        sbzjfsmap.put("02","设备增加-技术改造");
        sbzjfsmap.put("03","设备增加-零星购置");
        sbzjfsmap.put("04","设备增加-投资者投入");
        sbzjfsmap.put("05","设备增加-融资租入");
        sbzjfsmap.put("06","设备增加-债务重组取得");
        sbzjfsmap.put("07","设备增加-接受捐赠");
        sbzjfsmap.put("08","设备增加-无偿调入");
        sbzjfsmap.put("09","设备增加-盘盈");
        sbzjfsmap.put("10","设备增加-其他途径");

        Map<String, String> zyflmap = new HashMap<String, String>();
        zyflmap.put("1","输电");
        zyflmap.put("2","变电");
        zyflmap.put("3","配电");


        int index = 0;
        List<LineFeature> list = jdbcTemplate.query(sql, new RowMapper<LineFeature>() {
            @Override
            public LineFeature mapRow(ResultSet rs, int i) throws SQLException {
                LineFeature lf = new LineFeature();
                lf.setSBBM(rs.getString("SBBM"));
                lf.setDXMPYXKID(rs.getString("DXMPYXKID"));
                lf.setXLMC(rs.getString("XLMC"));
                lf.setSSDS(rs.getString("SSDS"));
                lf.setXLZCD(rs.getString("XLZCD"));
                lf.setJKXLCD(rs.getString("JKXLCD"));
                lf.setSGDW(rs.getString("SGDW"));
                lf.setTYRQ(rs.getString("TYRQ"));
                lf.setJLDW(rs.getString("JLDW"));
                lf.setZCDW(rs.getString("ZCDW"));
                lf.setZCBH(rs.getString("ZCBH"));
                lf.setSJDW(rs.getString("SJDW"));
                lf.setJSDW(rs.getString("JSDW"));
                lf.setWBSBH(rs.getString("WBSBH"));
                lf.setWBSMS(rs.getString("WBSMS"));
                lf.setZCDWMC(rs.getString("ZCDWMC"));
                lf.setSSDSMC(rs.getString("SSDSMC"));
                lf.setSWID(rs.getString("SWID"));

                // 补充需要关联字段
                String xlxz = rs.getString("XLXZ");
                if(xlxzmap.containsKey(xlxz)) {
                    lf.setXLXZ(xlxzmap.get(xlxz));
                }
                String yxzt = rs.getString("YXZT");
                if(yxztmap.containsKey(yxzt)) {
                    lf.setYXZT(yxztmap.get(yxzt));
                }
                String sbzjfs = rs.getString("SBZJFS");
                if(sbzjfsmap.containsKey(sbzjfs)) {
                    lf.setSBZJFS(sbzjfsmap.get(sbzjfs));
                }
                String zyfl = rs.getString("ZYFL");
                if(zyflmap.containsKey(zyfl)) {
                    lf.setZYFL(zyfl);
                }
                String zcxz = rs.getString("ZCXZ");
                if(zcxzmap.containsKey(zcxz)) {
                    lf.setZCXZ(zcxzmap.get(zcxz));
                }
                return lf;
            }
        });
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public void deleteUsers(String[] users) {
        String sqlFormat = "DELETE FROM line_inspector WHERE id in(%s)";
        String ids = String.join(",", users);
        String sql = String.format(sqlFormat, ids);
        jdbcTemplate.update(sql);
    }

    @Override
    public void delUser(String code) {
        String sqlFormat = "DELETE FROM line_inspector WHERE code='%s'";
        String sql = String.format(sqlFormat, code);
        System.out.println("删除用户:"+sql);
        jdbcTemplate.update(sql);
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