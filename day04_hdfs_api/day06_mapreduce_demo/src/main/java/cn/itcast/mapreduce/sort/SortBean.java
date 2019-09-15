package cn.itcast.mapreduce.sort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SortBean implements WritableComparable<SortBean> {

    private int num;

    private String word;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word +"\t"+ num;
    }

    /*
     实现比较器,指定排序的规则
     规则:
        优先比较: (word)按字典顺序进行排序
        word相同的时候,(num)再按升序比较
     */
    @Override
    public int compareTo(SortBean sortBean) {

        // 优先比较: (word)按字典顺序进行排序
        int result = this.word.compareTo(sortBean.word);
        // word相同的时候,(num)再按升序比较
        if (result == 0){
            return this.num - sortBean.num;
        }
        return result;
    }

    // 实现序列化
    @Override
    public void write(DataOutput out) throws IOException {

        out.writeUTF(word); // writeUTF()针对字符串的固定方法
        out.writeInt(num);  // writeUInt()针对整数的固定方法
    }

    //实现反序列化
    @Override
    public void readFields(DataInput in) throws IOException {

        this.word = in.readUTF();
        this.num = in.readInt();
    }
}
