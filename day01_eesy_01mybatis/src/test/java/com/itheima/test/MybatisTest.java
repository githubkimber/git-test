package com.itheima.test;

import com.itheima.User;
import com.itheima.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    /*D:\develop\idea_workspace\day01_eesy_01mybatis\src\test\java\com\itheima\test\MybatisTest.java
        测试Mybatis的环境搭建
     */
    public static void main(String[] args) throws IOException {

        // 1.读取配置文件
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml") ;
        // 2.根据配置文件构建sqlsessionFactory
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder() ;
        SqlSessionFactory factory = builder.build(in) ;
        // 3.使用sqlSessionFactory创建SQLSession对象
        SqlSession session = factory.openSession() ;
        // 4.使用SsqlSession构建Dao的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class) ;
        // 5.执行dao中的findAll方法
        List<User> users = userDao.findAll() ;
        for (User user : users){
            System.out.println(user);
        }
        // 6.释放资源
        session.close();
        in.close();
    }
}
