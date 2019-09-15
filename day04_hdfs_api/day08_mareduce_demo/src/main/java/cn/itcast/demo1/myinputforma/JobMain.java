package cn.itcast.demo1.myinputforma;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class JobMain extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        // 1.获取job对象(参数1:configuration; 参数2:任务名字 )
        Job job = Job.getInstance(super.getConf(), "sequence_file_job");

        // 2.设置job任务
        // 第一步:设置输入类和输入的路径(K1和V1)
        job.setInputFormatClass(MyInputForma.class);
        MyInputForma.addInputPath(job, new Path("file:///D:\\text\\input\\myInputformat_input"));
        // 第二步: 设置Mapper类和数据类型(K2和V2)
        job.setMapperClass(SequenceFileMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        // 第三,四,五,六步默认
        // (自定义inputformat特殊情况)第七步:不需要设置Reducer类,但是必须设置数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);
        // 第八步: 设置输出类和输出路径
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        SequenceFileOutputFormat.setOutputPath(job, new Path("file:///D:\\out\\myinputformat_out"));

        // 3.等待job任务执行结束
        boolean bl = job.waitForCompletion(true);
        return bl?0:1;
    }

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        // 启动job任务
        int run = ToolRunner.run(configuration, new JobMain(), args);
        System.exit(run);
    }
}
