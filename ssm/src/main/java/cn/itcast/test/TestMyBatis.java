package cn.itcast.test;


import cn.itcast.dao.AccountDao;
import cn.itcast.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMyBatis {

    /*
    测试查询
     */
    @Test
    public void run1() throws IOException {
        // 加载配置文件得到一个输入流
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 创建SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 创建SqlSession对象
        SqlSession session = factory.openSession();
        // 获取到代理对象
        AccountDao dao = session.getMapper(AccountDao.class);
        // 查询所有资源
        List<Account> list = dao.findall();
        for (Account account : list) {
            System.out.println(account);
        }
        // 关闭资源
        session.close();
        in.close();

    }

    /*
    测试保存
     */
    @Test
    public void run2() throws IOException {
        Account account = new Account();
        account.setName("熊大");
        account.setMoney(400d);

        // 加载配置文件得到一个输入流
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 创建SqlSessionFactory对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 创建SqlSession对象
        SqlSession session = factory.openSession();
        // 获取到代理对象
        AccountDao dao = session.getMapper(AccountDao.class);
        // 保存
        dao.saveAccount(account);

        // 查询所有资源(遍历显示是否保存成功)
        List<Account> list = dao.findall();
        for (Account accounts : list) {
            System.out.println(accounts);
        }

        // 提交事务
        session.commit();

        // 关闭资源
        session.close();
        in.close();
    }
}