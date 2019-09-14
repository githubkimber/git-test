package cn.itcast.common_frends_step1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.security.Key;

public class Step1Reducer extends Reducer<Text, Text, Text, Text> {

    // 将K2和V2转换为K3和V3
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        // 1.遍历集合,并将每个元素拼接,得到K3
        StringBuffer buffer = new StringBuffer();
        for (Text value : values){
            buffer.append(value.toString()).append("-");
        }
        // 2.K2就是V3
        // 3.将K3和V3写入上下文中
        context.write(new Text(buffer.toString()), key);
    }
}
