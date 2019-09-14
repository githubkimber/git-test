package cn.itcast.demo2.myoutputformat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MyOutputFormatMapper extends Mapper<LongWritable, Text, Text, NullWritable>{

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 将K2和V2写入文本数据(K2就是原来的V1,V2:NullWritable)
        context.write(value, NullWritable.get());
    }
}
