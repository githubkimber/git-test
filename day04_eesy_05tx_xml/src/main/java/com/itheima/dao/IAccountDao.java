package com.itheima.dao;

import com.itheima.domain.Account;

/*
账户的持久层接口
 */
public interface IAccountDao {

    /*
    根据Id查询账户
     */
    Account findAccountById(Integer accountId);

    /*
    根据名称查询账户
     */
    Account findAccountByName(String accountName);

    /*
    更新账户
     */
    void updateAccount(Account  account);
}
