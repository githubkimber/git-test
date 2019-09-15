package cn.itcast.demo3.mygrouping;


import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

// 1.继承WritableComparator
public class OrderGroupComparator extends WritableComparator{

    // 2.调用父类的有参构造
    public OrderGroupComparator() {
        super(OrderBean.class, true);
    }

    // 3.指定分组的规则(重写方法)
    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        // 3.1对形参做强制类型转换
        OrderBean first = (OrderBean) a ;
        OrderBean second = (OrderBean) b ;
        // 3.2制定分组规则
        return first.getOrderId().compareTo(second.getOrderId());
    }
}
