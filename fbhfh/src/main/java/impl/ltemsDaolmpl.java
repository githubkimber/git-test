package impl;

import dao.ltemsDao;
import domain.ltems;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ltemsDaolmpl implements ltemsDao {

    public List<ltems> findAll() throws Exception{

        List<ltems> list = new ArrayList<ltems>() ;

        Connection connection = null ;

        PreparedStatement pst = null;

        ResultSet rs = null ;

        try {
        //加载驱动类
        Class.forName("com.mysql.jdbc.Driver");
        // 先获取contection对象
        connection = DriverManager.getConnection("jdbc:mysql:///db1" ,"root","123");
        //获取真正操作数据的对象
        pst = connection.prepareCall("select * from USER1 ");
        //执行数据库操作
        rs = pst.executeQuery();
        //把数据库结果集转成Java的list集合
        list = new ArrayList<ltems>();
        while (rs.next()) {
            ltems items = new ltems();
            items.setId(rs.getInt("id"));
            items.setUsername(rs.getString("username"));
            list.add(items);
        }
    }catch(Exception e) {
        e.printStackTrace();
    }finally{
            connection.close();
            pst.close();
            rs.close();
    }
        return list ;
    }
}
