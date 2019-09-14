import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArrayListToFileTest_students {

    public static void main(String[] args) throws IOException {

        // 创建集合对象
        ArrayList<Students> array = new ArrayList<Students>();
        // 创建学生对象
        Students s1 = new Students("It001","张曼玉",35,"北京");
        Students s2 = new Students("It002","王祖贤",33,"上海");
        Students s3 = new Students("It003","林青霞",30,"西安");
        // 把学生对象添加到集合中
        array.add(s1);
        array.add(s2);
        array.add(s3);
        // 创建字符缓冲输出流对象
        BufferedWriter bw = new BufferedWriter(new FileWriter("Students.txt"));
        // 遍历集合,得到每一个学生对象,然后把该对象的数据拼接成一个指定格式的字符串写到文本文件
        for (Students s : array){
            // It001,张曼玉,35,北京
            StringBuilder sb = new StringBuilder();
            sb.append(s.getSid()).append(",").append(s.getName()).append(",").append(s.getAge()).append(",").append(s.getAdress());
            bw.write(sb.toString());
            bw.newLine();
            bw.flush();
        }
        // 释放资源
        bw.close();
    }
}
