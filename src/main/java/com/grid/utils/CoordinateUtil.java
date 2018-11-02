package com.grid.utils;

import static java.lang.StrictMath.*;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

/**
 * @author xiaoke 2018.10.16
 */
public class CoordinateUtil {

    private static final double pi = 3.14159265358979324;
    private static final double a = 6378245.0;
    private static final double ee = 0.00669342162296594323;
    private static final  double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    private static boolean outOfChina(double lat, double lon) {
        return lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271;

    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * sqrt(abs(x));
        ret += (20.0 * sin(6.0 * x * pi) + 20.0 * sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * sin(y * pi) + 40.0 * sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * sin(y / 12.0 * pi) + 320 * sin(y * pi / 30.0)) * 2.0 / 3.0;

        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * sqrt(abs(x));
        ret += (20.0 * sin(6.0 * x * pi) + 20.0 * sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * sin(x * pi) + 40.0 * sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * sin(x / 12.0 * pi) + 300.0 * sin(x / 30.0 * pi)) * 2.0 / 3.0;

        return ret;
    }

    /**
     * 地球坐标转换为火星坐标
     * World Geodetic System ==> Mars Geodetic System
     * Map: latitude,longitude
     *
     * @param wgs   Coordinate 地球坐标
     * @return  Coordinate 火星坐标
     */
    public static Coordinate transform2Mars(Coordinate wgs) {
        Coordinate marsCoordinate = new Coordinate();

        double wgLat = wgs.getLatitude();
        double wgLon = wgs.getLongitude();

        if (CoordinateUtil.outOfChina(wgLat, wgLon)) {
            marsCoordinate.setLongitude(wgLon);
            marsCoordinate.setLatitude(wgLat);

            return marsCoordinate;
        }

        // 地球坐标转火星坐标算法
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * cos(radLat) * pi);

        marsCoordinate.setLongitude(wgLon + dLon);
        marsCoordinate.setLatitude(wgLat + dLat);

        return marsCoordinate;
    }

    /**
     * 火星坐标转换为百度坐标
     * Mars Geodetic System ==> BaiDu Geodetic System
     * Map: latitude,longitude
     *
     * @param mgs   Coordinate 火星坐标
     * @return  Coordinate 百度坐标
     */
    public static Coordinate bdEncrypt(Coordinate mgs) {
        Coordinate bdCoordinate = new Coordinate();

        double x = mgs.getLongitude();
        double y = mgs.getLatitude();
        double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);

        bdCoordinate.setLongitude(z * cos(theta) + 0.0065);
        bdCoordinate.setLatitude(z * sin(theta) + 0.006);

        return bdCoordinate;
    }

    /**
     * 百度坐标转火星坐标
     * BaiDu Geodetic System ==> Mars Geodetic System
     *
     * @param bgs   Coordinate 百度坐标
     * @return  Coordinate 火星坐标
     */
    public static Coordinate bdDecrypt(Coordinate bgs) {
        Coordinate marsCoordinate = new Coordinate();

        double x = bgs.getLongitude() - 0.0065;
        double y = bgs.getLatitude() - 0.006;
        double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);

        marsCoordinate.setLongitude(z * cos(theta));
        marsCoordinate.setLatitude(z * sin(theta));

        return marsCoordinate;
    }


    public static void main(String[] args) {
        // 百度坐标
//        116.40727,39.978862
        // 高德坐标
//        39.9104924347,116.3774872222
//        39.9179897426,116.3901633017

        Coordinate coordinate = new Coordinate();
        //34.42257,104.90776
        coordinate.setLongitude(104.90776);
        coordinate.setLatitude(34.42257);


        Coordinate marsCoordinate = transform2Mars(coordinate);
//        Coordinate bdCoordinate = bdEncrypt(marsCoordinate);
        System.out.println(marsCoordinate.getLongitude());
        System.out.println(marsCoordinate.getLatitude());
    }
}

