package com.grid.dal.dao;

import com.grid.dal.domain.LineUsers;
import com.grid.dal.domain.LineUsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LineUsersMapper {
    long countByExample(LineUsersExample example);

    int deleteByExample(LineUsersExample example);

    int deleteByPrimaryKey(Short id);

    int insert(LineUsers record);

    int insertSelective(LineUsers record);

    List<LineUsers> selectByExample(LineUsersExample example);

    LineUsers selectByPrimaryKey(Short id);

    int updateByExampleSelective(@Param("record") LineUsers record, @Param("example") LineUsersExample example);

    int updateByExample(@Param("record") LineUsers record, @Param("example") LineUsersExample example);

    int updateByPrimaryKeySelective(LineUsers record);

    int updateByPrimaryKey(LineUsers record);
}