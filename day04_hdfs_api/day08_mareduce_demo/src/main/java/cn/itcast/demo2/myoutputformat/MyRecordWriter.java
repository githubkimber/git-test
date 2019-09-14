package cn.itcast.demo2.myoutputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class MyRecordWriter extends RecordWriter<Text, NullWritable>{

    private FSDataOutputStream goodCommentsOutputStream ;
    private FSDataOutputStream badCommentsOutputStream ;

    public MyRecordWriter() {
    }

    public MyRecordWriter(FSDataOutputStream goodCommentsOutputStream, FSDataOutputStream badCommentsOutputStream) {
        this.goodCommentsOutputStream = goodCommentsOutputStream;
        this.badCommentsOutputStream = badCommentsOutputStream;
    }

    /*
    区分数据写入不同文件夹; (参数1:行文本内容)
    */
    @Override
    public void write(Text text, NullWritable nullWritable) throws IOException, InterruptedException {

        // 1.从行文本数据中获取第9个字段
        String[] split = text.toString().split("\t");
        String numStr = split[9];
        // 2.根据字段的值,判断评论的类型,然后将对应的数据写入不同的文件夹文件中(0:好评, 1:中评, 2:差评)
        if (Integer.parseInt(numStr) <= 1 ){
            // 好评或者中评
            goodCommentsOutputStream.write(text.toString().getBytes());
            goodCommentsOutputStream.write("\r\n".getBytes());

        }else{
            // 差评
            badCommentsOutputStream.write(text.toString().getBytes());
            badCommentsOutputStream.write("\r\n".getBytes());
        }

    }

    // 释放资源
    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {

        IOUtils.closeStream(goodCommentsOutputStream);
        IOUtils.closeStream(badCommentsOutputStream);
    }
}
