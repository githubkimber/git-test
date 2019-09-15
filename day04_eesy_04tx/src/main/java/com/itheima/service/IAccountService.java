package com.itheima.service;

import com.itheima.domain.Account;

/*
账户的业务层接口
 */
public interface IAccountService {

    /*
    根据Id查询账户信息
     */
    Account findAccountById(Integer accountId) ;

    /*
    转账
    sourceName: 转成账户名称
    targetName: 转入账户名称
    money:      转账金额
     */
    void transfer(String sourceName,String targetName,Float money) ;
}
