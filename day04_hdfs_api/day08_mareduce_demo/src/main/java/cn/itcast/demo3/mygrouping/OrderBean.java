package cn.itcast.demo3.mygrouping;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean>{

    private String orderId;
    private Double price;

    @Override
    public String toString() {
        return  orderId + "\t" + price ;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // 指定排序规则
    @Override
    public int compareTo(OrderBean orderBean) {
        // 先比较订单Id,如果订单Id一致则排序订单金额(降序)
        int i = this.orderId.compareTo(orderBean.orderId);
        if (i==0){
            i = this.price.compareTo(orderBean.price)*(-1);
        }
        return i;
    }

    // 实现对象的序列化
    @Override
    public void write(DataOutput out) throws IOException {

        out.writeUTF(orderId);  // 写String类型
        out.writeDouble(price); // 写Double类型
    }

    // 实现对象的反序列化
    @Override
    public void readFields(DataInput in) throws IOException {

        this.orderId = in.readUTF();
        this.price = in.readDouble();
    }
}
