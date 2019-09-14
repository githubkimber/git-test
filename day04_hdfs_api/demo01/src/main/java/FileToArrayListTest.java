import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileToArrayListTest {

    public static void main(String[] args) throws IOException {

        // 创建字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new FileReader("array.txt"));
        // 创建集合对象
        ArrayList<String> array = new ArrayList<String>();
        // 读取数据,每次读取一行,并把该数据作为元素存储到集合中
        String line;
        while ((line = br.readLine()) != null) {
            array.add(line);
        }
        // 释放资源
        br.close();
        // 遍历集合
        for(String s : array){
            System.out.println(s);
        }
    }
}
