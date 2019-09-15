package com.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/*
和Spring连接数据库相关的配置类
 */
public class JdbcConfig {
    @Value(value="${jdbc.driver}")
    private String driver ;
    @Value(value="${jdbc.url}")
    private String url ;
    @Value(value="${jdbc.username}")
    private String username ;
    @Value(value="${jdbc.password}")
    private String password ;

    /*
    用于创建一个QueryRunner对象
     */
    @Bean(name = "runner")
    @Scope(value="prototype")
    public QueryRunner createQueryRunner(@Qualifier("dataSource2") DataSource dataSource){
                                        // 用于指定是谁给来参数赋值
        return new QueryRunner(dataSource) ;
    }

    /*
    创建数据源对象
     */
    @Bean(name="dataSource")
    public DataSource createDataSource(){
   try {
        ComboPooledDataSource ds = new ComboPooledDataSource() ;
        ds.setDriverClass(driver) ;
        ds.setJdbcUrl(url) ;
        ds.setUser(username) ;
        ds.setPassword(password) ;
        return ds ;
   } catch (Exception e) {
      throw new RuntimeException(e) ;
   }
    }

    @Bean(name="dataSource2")
    public DataSource createDataSource2(){
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource() ;
            ds.setDriverClass(driver) ;
            ds.setJdbcUrl(url) ;
            ds.setUser(username) ;
            ds.setPassword(password) ;
            return ds ;
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }
}
