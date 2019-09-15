package cn.itcast.mapreduce.fllow_count_demo1;

import cn.itcast.mapreduce.flow_count_sort_demo2.FlowBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class JobMain extends Configured implements Tool {

    // 该方法用于指定一个Job任务
    @Override
    public int run(String[] args) throws Exception {
        // 1.创建一个job任务对象    (super.getConf():获取Configuration对象)
        Job job = Job.getInstance(super.getConf(), "mapreduce_flowcount");
        // 如果打包运行出错, 则需要加该配置
        job.setJarByClass(JobMain.class);
        // 2.配置job任务对象(八个步骤)
        // 第一步:指定文件的读取方式和读取路径
        job.setInputFormatClass(TextInputFormat.class);
        // hdfs文件系统地址:
//        TextInputFormat.addInputPath(job, new Path("hdfs://node01:8020/wordcount"));
        // 本地文件系统地址:(linux:用/, windows:用\)
       TextInputFormat.addInputPath(job, new Path("file:///D:\\text\\input\\flowcount_input"));
        // 第二步:指定Map阶段的处理方式和数据类型
        job.setMapperClass(FlowCountMapper.class);
        // 设置Map阶段K2的类型
        job.setMapOutputKeyClass(Text.class);
        // 设置Map阶段V2的阶段
        job.setMapOutputValueClass(FlowBean.class);
        // 第三(分区),四(排序)默认
        // 第五(规约指定Combiner)
//        job.setCombinerClass(MyCombiner.class);
        // 第六步(分组)默认
        // 第七步:指定Reduce阶段的处理方式和数据类型
        job.setReducerClass(FlowCountReducer.class);
        // 设置K3的类型
        job.setOutputKeyClass(Text.class);
        // 设置V3的类型
        job.setOutputValueClass(FlowBean.class);
        // 第八步:设置输出类型
        job.setOutputFormatClass(TextOutputFormat.class);
        //设置输出路径
//        Path path = new Path("hdfs://node01:8020/wordcount_out");
        Path path = new Path("file:///D:\\text\\out\\flowcount_out");
        TextOutputFormat.setOutputPath(job, path);
//      TextOutputFormat.setOutputPath(job, new Path("file:///D:\\text\\mapreduce\\output"));

        // 获取FileSystem
/*        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());
        FileSystem fileSystem = FileSystem.get(new URI("file:///D:\\text\\mapreduce\\output"), new Configuration());
        // 判断目录是否存在
        boolean bl2 = fileSystem.exists(path);
        if (bl2){
            // 删除目标目录(参数1:路径; 参数2:是否递归删除)
            fileSystem.delete(path, true);
        }
*/
        // 等待任务结束
        boolean bl = job.waitForCompletion(true);
        // bl为true时返回0正常,bl为false时返回1不正常
        return bl?0:1;
    }

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        // 启动job任务
        int run = ToolRunner.run(configuration, new JobMain(), args);
        System.exit(run);
    }
}
