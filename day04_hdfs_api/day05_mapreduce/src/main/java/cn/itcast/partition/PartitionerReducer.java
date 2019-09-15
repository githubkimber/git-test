package cn.itcast.partition;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
K2: Text
V2: NullWritable
K3:Text
V3:NullWritable
定义Reduce逻辑,不作任何处理,将数据原封不动的输出即可
 */
public class PartitionerReducer extends Reducer{

    public static enum Counter{
        MY_INPUT_RECOREDS, MY_INPUT_BYTES
    }

    @Override
    protected void reduce(Object key, Iterable values, Context context) throws IOException, InterruptedException {

        // 方式2: 使用枚举来定义计数器
        context.getCounter(Counter.MY_INPUT_RECOREDS).increment(1L);

        context.write(key, NullWritable.get());
    }
}
