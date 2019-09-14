import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileToArrayListTest_Students {

    public static void main(String[] args) throws IOException {

        // 创建字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new FileReader("students.txt"));
        // 创建集合对象
        ArrayList<Students> array = new ArrayList<Students>();
        // 读取数据,每次读取一行数据,把该行数据想办法封装成学生对象,并把学生对象存储到集合
        String line;
        while ((line = br.readLine()) != null){
            String[] strArray = line.split(",");
            Students s = new Students();
            s.setSid(strArray[0]);
            s.setName(strArray[1]);
            s.setAge(Integer.parseInt(strArray[2]));
            s.setAdress(strArray[3]);
            array.add(s);
        }
        // 释放资源
        br.close();
        for (Students s : array){
            System.out.println(s.getSid()+"---"+s.getName()+"---"+s.getAge()+"---"+s.getAdress());
        }
    }
}
