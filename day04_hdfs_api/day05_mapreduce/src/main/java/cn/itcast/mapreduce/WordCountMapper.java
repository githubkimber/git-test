package cn.itcast.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
Mapper类:有四个泛型:
KEYIN: K1的类型
VALUEIN: V1的类型0
KEYOUT: K2的类型
VALUEOUT: V2的类型
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable>{

    /*
    map方法就是将K1和V1转换为K2为V2
    参数:
        key:    K1 行偏移量`
        value:  V1 每一行的文本数据
        context: 表示上下文对象
    如何将K1和V1转换为K2和V2
    0   hello,world,hadoop
    15  hdfs,hive,hello.
    -------------------------
    K2          V2
    hello        1
    world        1
    hdfs         1
    hadoop       1
    hello        1
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

      Text text = new Text();
      LongWritable longWritable = new LongWritable();

       // 1.将一行的文本进行拆分
       String[] split = value.toString().split(",");
       // 2.遍历数组, 组装 K2和V2
       for (String world : split){
           // 3.将K2和V2写入上下文
           text.set(world);
           longWritable.set(1);
           context.write(text,longWritable);
       }




    }
}



