package com.grid.dal.dao;

import com.grid.dal.domain.LineInspector;
import com.grid.dal.domain.LineInspectorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LineInspectorMapper {
    long countByExample(LineInspectorExample example);

    int deleteByExample(LineInspectorExample example);

    int deleteByPrimaryKey(Short id);

    int insert(LineInspector record);

    int insertSelective(LineInspector record);

    List<LineInspector> selectByExample(LineInspectorExample example);

    LineInspector selectByPrimaryKey(Short id);

    int updateByExampleSelective(@Param("record") LineInspector record, @Param("example") LineInspectorExample example);

    int updateByExample(@Param("record") LineInspector record, @Param("example") LineInspectorExample example);

    int updateByPrimaryKeySelective(LineInspector record);

    int updateByPrimaryKey(LineInspector record);

    List<LineInspector> listUsers(String users);
}