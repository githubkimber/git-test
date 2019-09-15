package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.factory.BeanFactory;

/*
账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao{

    private IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao") ;
//    private int i= 1 ;

    public void saveAccount(){
        int i= 1 ;
        System.out.println("保存了账户!");
        System.out.println(i);
        i++;
    }
}
