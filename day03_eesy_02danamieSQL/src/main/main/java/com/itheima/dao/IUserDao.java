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

    /*
    根据名称模糊查询用户信息
     */
    List<User> findByName(String username);

    /*
    根据queryVo中的条件查询用户
     */
    List<User> findUserByVo(QueryVo vo) ;

    /*
    根据传入参数条件查询数据信息;
     */
    List<User> findUserByCondition(User user) ;
}