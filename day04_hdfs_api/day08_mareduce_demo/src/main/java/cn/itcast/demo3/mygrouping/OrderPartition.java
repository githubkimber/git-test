package cn.itcast.demo3.mygrouping;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class OrderPartition extends Partitioner<OrderBean, Text> {

    /*
    指定分区规则:根据订单的Id实现分区
    OrderBean: K2
    Text: V2
    i:    ReduceTask个数
    返回值: 返回分区的编号
     */
    @Override
    public int getPartition(OrderBean orderBean, Text text, int i) {
        return (orderBean.getOrderId().hashCode() & 2147483647) % i ;
    }
}
