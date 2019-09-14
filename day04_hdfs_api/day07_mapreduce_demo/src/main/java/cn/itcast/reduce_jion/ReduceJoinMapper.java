package cn.itcast.reduce_jion;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
K1:     LongWritable
V1:     Text
-----------------------
K2:     Text    商品Id
V2:     Text    行文本信息(商品的信息)
 */
public class ReduceJoinMapper extends Mapper<LongWritable, Text, Text, Text>{

    /*
    product.txt     K1          V1
                    0           p0001,小米5,1000,2000
   ------------------------------------------------------
   orders.text      K1          V1
                    0           1001,20150710,p0001,2

 ----------------------------------------------------------
                    K2          V2
                    p0001       p0001,小米5,1000,2000
                    p0001       1001,20150710,p0001,2
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 1.判断数据来自哪个文件
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fileName = fileSplit.getPath().getName();
        if (fileName.equals("product.txt")) {
            // 数据来自商品表
            // 2.將K1和V1转换为K2和V2,写入上下文中(先分割在截取行文本中的K2和V2)
            String[] split = value.toString().split(",");
            String productId = split[0];

            context.write(new Text(productId), value);
        }else{
            // 数据来自订单表
            // 2.將K1和V1转换为K2和V2,写入上下文中(先分割在截取行文本中的K2和V2)
            String[] split = value.toString().split(",");
            String productId = split[2];

            context.write(new Text(productId), value);
        }
    }
}
