package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/*
账户的持久层实现类
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao{

    @Autowired
    private QueryRunner runner ;

    public List<Account> findAllAccount() {

        try {
            return runner.query("select * from account " , new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
           throw new RuntimeException(e) ;
        }
    }

    public Account findAccountById(Integer accountId) {

        try {
            return runner.query("select * from account " , new BeanHandler<Account>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }

    public void saveAccount(Account account) {

        try {
            runner.update("insert into account(name,money)value(?,?) " , account.getName(),account.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }

    public void updateAccount(Account account) {

        try {
            runner.update("update account set name=?,money=? where id=? " , account.getName(),account.getMoney(),account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }

    public void deleteAccount(Integer accountId) {

        try {
            runner.update("delete from account where id = ?" , accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }
}
