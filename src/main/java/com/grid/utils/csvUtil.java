package com.grid.utils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

import com.csvreader.CsvReader;

public class csvUtil {
    public static Map<String, Map<String, List>> readVillages(InputStream is){
        Map<String, List> city = new HashMap<String,List>();
        Map<String, List> county = new HashMap<String,List>();
        Map<String, List> street = new HashMap<String,List>();
        Map<String, List> village = new HashMap<String,List>();
        List cts = new ArrayList();
        List ccounts = new ArrayList();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();

            CsvReader reader = new CsvReader(is,',', Charset.forName("UTF-8"));
//          reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            System.out.println("读取的行数："+csvList.size());

            for(int row=0;row<csvList.size();row++){
                //打印每一行的数据
                cts.add(csvList.get(row)[0]);
                ccounts.add(csvList.get(row)[1]);
                if(street.containsKey(csvList.get(row)[1])) {
                    List dsf = street.get(csvList.get(row)[1]);
                    dsf.add(csvList.get(row)[2]);
                    street.put(csvList.get(row)[1], dsf);
                }else {
                    List ls = new ArrayList();
                    ls.add(csvList.get(row)[2]);
                    street.put(csvList.get(row)[1],ls);
                }

                if (village.containsKey(csvList.get(row)[2])) {
                    List lll = village.get(csvList.get(row)[2]);
                    lll.add(csvList.get(row)[3]);
                    village.put(csvList.get(row)[2], lll);
                }else {
                    List ls = new ArrayList();
                    ls.add(csvList.get(row)[3]);
                    village.put(csvList.get(row)[2],ls);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cts = reduce(cts);
        ccounts = reduce(ccounts);
        city.put("city", cts);
        county.put("county", ccounts);
        Map<String,Map<String,List>> result = new HashMap<String,Map<String,List>>();
        result.put("city",city);
        result.put("county",county);
        result.put("street",street);
        result.put("village", village);
        return result;
    }

    public static List reduce(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

}
