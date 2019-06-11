package com.grid.dao;


import com.grid.dal.dao.LineInspectorMapper;
import com.grid.dal.dao.LineUsersMapper;
import com.grid.dal.domain.LineInspector;
import com.grid.dal.domain.LineInspectorExample;
import com.grid.dal.domain.LineUsers;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {

    @Resource
    private LineInspectorMapper lineInspectorMapper;
    @Resource
    private LineUsersMapper lineUsersMapper;

    // 设置自增主键
    /* 先创建序列
    drop sequence dectuser_tb_seq;
    create sequence dectuser_tb_seq minvalue 1 maxvalue 99999999
             increment by 1
             start with 1;
     */

    /* 创建触发器
     create or replace trigger dectuser_tb_tri_lineusers
    before insert on lineusers
     for each row
    begin
    select  dectuser_tb_seq.nextval into :new.id from dual;
    dectuser_tb_seq
    end;
     */

    @Override
    public LineInspector exists(LineInspector inspector) {
        LineInspectorExample example = new LineInspectorExample();
        LineInspectorExample.Criteria criteria = example.createCriteria();
        System.out.println(inspector.getCode());
        criteria.andCodeEqualTo(inspector.getCode());
        List<LineInspector> lineInspectors = lineInspectorMapper.selectByExample(example);
        if(lineInspectors.size() > 0) {
            System.out.println("已找到");
            return lineInspectors.get(0);
        }
        // 不存在，插入记录
        lineInspectorMapper.insert(inspector);

        return inspector;
    }

    @Override
    public int AddUserLine(LineUsers lineUsers) {
        System.out.println("添加对应关系"+lineUsers);
        return lineUsersMapper.insert(lineUsers);
    }

    @Override
    public List<LineInspector> listUsers(String[] users) {
        String allstr = String.join(",", users);
        return lineInspectorMapper.listUsers(allstr);
    }

}
