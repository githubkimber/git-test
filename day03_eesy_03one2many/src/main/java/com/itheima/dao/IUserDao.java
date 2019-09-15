package com.itheima.dao;

import com.itheima.domain.User;

import java.util.List;

public interface IUserDao {

    /*
    查询所有用户
     */
    List<User> findAll();

    /*
    根据Id查询用户信息
     */
    User findById(Integer userId);


}