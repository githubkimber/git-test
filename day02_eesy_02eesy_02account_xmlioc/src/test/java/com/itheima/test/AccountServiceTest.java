package com.itheima.test;

import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/*
使用Junit单元测试:测试我们的配置
 */
public class AccountServiceTest {
    @Test
    public void testFindAll() {
        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml") ;
        // 2.得到业务层对象
        IAccountService as = ac.getBean("accountService",IAccountService.class) ;
        // 3.执行方法
        List<Account> accounts = as.findAllAccount() ;
        // 4.遍历打印
        for(Account account : accounts){
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne() {
        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml") ;
        // 2.得到业务层对象
        IAccountService as = ac.getBean("accountService",IAccountService.class) ;
        // 3.执行方法
        Account account = as.findAccountById(1) ;
        // 4.打印
        System.out.println(account);
    }

    @Test
    public void testSave() {
        // 获取account对象
        Account account = new Account() ;
        // 给对象赋值
        account.setName("test") ; account.setMoney(1234f) ;
        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml") ;
        // 2.得到业务层对象
        IAccountService as = ac.getBean("accountService",IAccountService.class) ;
        // 3.执行方法
        as.saveAccount(account);

    }

    @Test
    public void testUpdate() {
        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml") ;
        // 2.得到业务层对象
        IAccountService as = ac.getBean("accountService",IAccountService.class) ;
        // 3.执行方法
        Account account = as.findAccountById(4) ;
        account.setMoney(2345f);
        as.updateAccount(account);

    }

    @Test
    public void testDelete() {
        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml") ;
        // 2.得到业务层对象
        IAccountService as = ac.getBean("accountService",IAccountService.class) ;
        // 3.执行方法
        as.deleteAccount(4);

    }
}
