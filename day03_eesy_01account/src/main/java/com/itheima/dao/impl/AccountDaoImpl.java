package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/*
账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao{

    private QueryRunner runner ; private ConnectionUtils connectionUtils ;

    public void setRunner(QueryRunner runner) {
        this.runner = runner;
    }

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public List<Account> findAllAccount() {

        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from account " , new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
           throw new RuntimeException(e) ;
        }
    }

    public Account findAccountById(Integer accountId) {

        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from account " , new BeanHandler<Account>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }

    public void saveAccount(Account account) {

        try {
            runner.update(connectionUtils.getThreadConnection(),"insert into account(name,money)value(?,?) " , account.getName(),account.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }

    public void updateAccount(Account account) {

        try {
            runner.update(connectionUtils.getThreadConnection(),"update account set name=?,money=? where id=? " , account.getName(),account.getMoney(),account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }

    public void deleteAccount(Integer accountId) {

        try {
            runner.update(connectionUtils.getThreadConnection(),"delete from account where id = ?" , accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }

    /*
    根据名称查询账户
    accountName
    如果有唯一的一个结果就返回,如果没有结果就返回null
    如果结果集超过一个就抛异常
     */
    public Account findAccountByName(String accountName) {
        try {
            List<Account> accounts = runner.query(connectionUtils.getThreadConnection(),"select * from account where name = ?" , new BeanListHandler<Account>(Account.class), accountName) ;
            if(accounts == null || accounts.size() == 0) {
                return null;
            }
            if (accounts.size() > 1){
                throw new RuntimeException("结果集不为零,数据有问题") ;
            }
            return accounts.get(0) ;
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
    }
}
