package cn.itcast.map_join;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;


public class MapJoinMapper extends Mapper<LongWritable, Text, Text, Text>{

    HashMap<String,String> map = new HashMap<>();

    // 第一件事情:将分布式缓存的小表数据读取到本地Map集合(只需要做一次)

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        // 1.获取分布式缓存文件列表
        URI[] cacheFiles = context.getCacheFiles();
        // 2.获取指定的分布式缓存文件的文件系统(FileSystem)
        FileSystem fileSystem = FileSystem.get(cacheFiles[0], context.getConfiguration());
        // 3.获取文件的输入流
        FSDataInputStream inputSteam = fileSystem.open(new Path(cacheFiles[0]));
        // 4.读取文件内容,并将数据存入Map集合
        // 4.1将字节输入流转为字符缓冲流FSDataInputStream---->bufferedReader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputSteam));
        // 4.2读取小表文件内容,以行为单位,并读取的数据存入Map集合
        String line = null;
        // 读取一行的文本数据
        while((line = bufferedReader.readLine())!=null){
            String[] split = line.split(",");
            map.put(split[0], line);

        }
        // 5.关闭流
        bufferedReader.close();
        fileSystem.close();
    }


    // 第二件事情:对大表的处理业务逻辑,而且要实现大表和小表的join操作

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 1.从行文本数据中获取商品的Id:p0001, p0002 得到K2
        String[] split = value.toString().split(",");
        String productId = split[2] ;   // K2
        // 2.在Map集合中,将商品的Id作为键,获取值(商品的行文本数据),将value和值拼接,得到V2
        String productLine = map.get(productId);
        String valueLine = productLine+"\t"+value.toString();   // V2
        // 3.将K2和V2写入上行下文中
        context.write(new Text(productId), new Text(valueLine));
    }
}
