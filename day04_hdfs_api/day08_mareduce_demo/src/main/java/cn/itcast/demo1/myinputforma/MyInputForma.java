package cn.itcast.demo1.myinputforma;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import java.io.IOException;


public class MyInputForma extends FileInputFormat{

    @Override
    public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {

        // 1.创建自定义RecordReader对象
        MyRecordReader myRecordReader = new MyRecordReader();
        // 2.将inputSplit和context对象(taskAttemptContext)传给MyRecordReader
        myRecordReader.initialize(inputSplit, taskAttemptContext);

        return myRecordReader;
    }

    /*
    设置文件是否被切割
     */
    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false; //false:不可切割
    }
}
