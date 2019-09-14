package cn.itcast.demo3.mygrouping;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
                                            // K2, V2,      K3  ,V3
public class GroupReducer extends Reducer<OrderBean, Text, Text, NullWritable> {

    @Override
    protected void reduce(OrderBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        int i = 0 ;
        // 获取top1, K3和V3
        for (Text value : values){
            context.write(value, NullWritable.get());
            i++ ;
            if (i >= 2 ){
                break ;
            }
        }
    }
}
