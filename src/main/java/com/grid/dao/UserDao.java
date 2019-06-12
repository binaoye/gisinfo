package com.grid.dao;

import com.grid.dal.domain.Inspectors;
import com.grid.dal.domain.LineInspector;
import com.grid.dal.domain.LineUsers;

import java.util.List;

public interface UserDao {
    LineInspector exists(LineInspector inspector);
    int AddUserLine(LineUsers lineUsers);
    List<Inspectors> listUsers(String[] users);
    List<Inspectors> listAllUsers();
    List<Inspectors> listCityUsers(String city);
    List<Inspectors> listLineUsers(Long line);
}
