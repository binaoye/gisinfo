package com.grid.dao;

import com.grid.dal.domain.LineInspector;
import com.grid.dal.domain.LineUsers;

import java.util.List;

public interface UserDao {
    LineInspector exists(LineInspector inspector);
    int AddUserLine(LineUsers lineUsers);
    List<LineInspector> listUsers(String[] users);
}
