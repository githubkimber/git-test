package com.itheima.jdbctemplate;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
JdbcTemplate的基本用法
 */
public class JdbcTemplateDemo4 {
    public static void main(String[] args) {

        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml") ;
        // 2.获取对象
        IAccountDao accountDao = ac.getBean("accountDao",IAccountDao.class) ;
        // 根据Id查询
        Account account = accountDao.findAccountById(5) ;
        System.out.println(account) ;
        // 更改金额
        account.setMoney(3000f) ;
        accountDao.updateAccount(account) ;
    }
}
