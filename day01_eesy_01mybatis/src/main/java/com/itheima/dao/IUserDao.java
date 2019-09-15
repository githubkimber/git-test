package com.itheima.dao;

import com.itheima.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    List<User> findAll() ;
}
