package com.itheima.jdbc;

import java.sql.*;

/*
程序的耦合
 */
public class JdbcDemol {

    public static void main (String[] args) throws SQLException {

        // 1.注册驱动
        DriverManager.registerDriver(new com.mysql.jdbc.Driver()) ;
        // 2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1" , "root" , "123") ;
        // 3.获取操作数据库的预处理对象
        PreparedStatement pstm = conn. prepareStatement("select * from account ") ;
        // 4.执行sql,得到结果集
        ResultSet rs = pstm.executeQuery() ;
        // 5.遍历结果集
        while(rs.next()){
            System.out.println(rs.getString("name"));
        }
        // 6.释放资源
        rs.close();
        pstm.close();
        conn.close();
    }

}
