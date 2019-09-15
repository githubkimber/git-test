import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArrayListToFileTest {

    public static void main(String[] args) throws IOException {

        // 创建集合对象
        ArrayList<String> array = new ArrayList<String>();
        // 往集合中添加字符串元素
        array.add("hello");
        array.add("world");
        array.add("java");
        // 创建字符缓冲输出流对象                                          // 创建文件在相对目录
        BufferedWriter bw = new BufferedWriter(new FileWriter("array.txt"));
        // 遍历集合, 得到每一个字符串元素,把字符串元素作为数据写入到文本文件
        for (String s : array ){
            bw.write(s);    // 写入
            bw.newLine();   // 换行符
            bw.flush();     // 刷新
        }
        // 释放资源
        bw.close();
    }
}
