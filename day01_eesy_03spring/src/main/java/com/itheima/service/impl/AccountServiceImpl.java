package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.impl.AccountDaoImpl;

import com.itheima.service.IAccountService;

/*
账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService{

  //    private IAccountDao accountDao = new AccountDaoImpl();
  // 降低耦和
  private IAccountDao accountDao ;

      public AccountServiceImpl(){
          System.out.println("对象创建了!");
      }

    public void saveAccount(){

        accountDao.saveAccount() ;
    }
}
