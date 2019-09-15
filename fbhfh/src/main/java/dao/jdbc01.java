package dao;

public class jdbc01 {

/*    jdbc01操作:
       a.注册驱动
       Class.forName("com.mysql.jdbc.Driver")
       b.获取连接
       Connection conn = DriverManager.getConnection(url,username,password)
       c.获取与处理对象
       preparedStatement pstm = conn.prepareStatement("Select * from table") ;
       d.执行方法 (可以是增删改, 也可以是查询)  假定:查询
       ResulSet rs = pstm.executequery() ;
       e.封装结果集
       while(rs.close()){}
       f.释放资源
       rs.close() ;
       pstm.close() ;
       conn.close() ;
 */


1