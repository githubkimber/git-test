package com.itheima.service.impl;

import com.itheima.service.IAccountService;

/*
账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService{
    public void saveAccount() {

        System.out.println("执行了保存") ; // int i = 1/0 ;
    }

    public void updateAccount(int i) {
        System.out.println("执行了更新"+i);
    }

    public void deleteAccount() {
        System.out.println("执行了删除");
        ;
    }

    public void aroundPringLog() {
        System.out.println("执行了环绕");
    }
}
