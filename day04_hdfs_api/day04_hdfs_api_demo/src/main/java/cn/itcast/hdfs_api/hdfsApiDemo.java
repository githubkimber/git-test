package cn.itcast.hdfs_api;


import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.*;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class hdfsApiDemo {

    /*
    小文件的合并
     */
    @Test
    public void mergeFile() throws URISyntaxException, IOException, InterruptedException {
        // 1.获取FileSystem(分布式文件系统)
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration(),"root");
        // 2.获取hdfs大文件的输出流
        FSDataOutputStream outputStream = fileSystem.create(new Path("/big_txt.txt"));
        // 3.获取一个本地文件系统
        LocalFileSystem localFileSystem = FileSystem.getLocal(new Configuration());
        // 4.获取本地文件夹下所有文件的详情(一个数组)
        FileStatus[] fileStatuses = localFileSystem.listStatus(new Path("D:\\input"));
        // 5.遍历每个文件,获取每个文件的输入流
        for (FileStatus fileStatus : fileStatuses) {
            FSDataInputStream inputStream = localFileSystem.open(fileStatus.getPath());

            // 6.将小文件的数据复制到大文件(每次产生一个inputStream,都需要关闭)
            IOUtils.copy(inputStream, outputStream);
            IOUtils.closeQuietly(inputStream);
        }
        // 7.关闭流
        IOUtils.closeQuietly(outputStream);
        localFileSystem.close();
        fileSystem.close();
    }


    /*
    文件的上传
     */
    @Test
    public void uploadFile()throws URISyntaxException, IOException{
        // 1.获取FileSystem
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());
        // 2.调用方法,实现文件的上传
        fileSystem.copyFromLocalFile(new Path("D://set.xml"), new Path("/"));
        // 3.关闭FileSystem
        fileSystem.close();
    }

    /*
   hdfs实现文件的下载方式2: copyToLocalFile()
    */
    @Test
    public void downloadFile2() throws URISyntaxException, IOException, InterruptedException {
        // 1.获取FileSystem
//        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());
        // 1.2.在用户权限关闭的的情况下,伪造用户身份进行访问下载
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration(), "root");
        // 2.调用方法,实现文件的下载
        fileSystem.copyToLocalFile(new Path("/a.txt"), new Path("D://a4.txt"));
        // 3.关闭FileSystem
        fileSystem.close();
    }

    /*
    hdfs实现文件的下载方式1
     */
    @Test
    public void downloadFile() throws URISyntaxException, IOException {
        // 1.获取FileSystem
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());
        // 2.获取hdfs的输入流
        FSDataInputStream inputStream = fileSystem.open(new Path("/a.txt"));
        // 3.获取本地路径的输出流
        FileOutputStream outputStream = new FileOutputStream("D://a.txt");
        // 4.文件的拷贝
        IOUtils.copy(inputStream,outputStream);
        // 5.关闭流
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
        fileSystem.close();
    }

    /*
  hdfs创建文件夹
   */
    @Test
    public void mkdirsTest() throws URISyntaxException, IOException {
        // 1.获取FileSystem实例
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());
        // 2.创建文件夹
        boolean bl = fileSystem.mkdirs(new Path("/aa/bb/cc"));
        System.out.println(bl);
        // 创建文件
        fileSystem.create(new Path("/aa/bb/cc/a.txt"));

        // 3.关闭fileSystem
        fileSystem.close();
    }


    /*
  hdfs文件的遍历
   */
    @Test
    public void listFiles() throws URISyntaxException, IOException {
        // 1.获取FileSystem实例
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());
        // 2.调用方法ListFiles, 获取/目录下所有的文件信息
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path("/"), true);
        // 3.便利迭代器信息
        while (iterator.hasNext()){
            LocatedFileStatus fileStatus = iterator.next() ;
            // 获取文件的绝对路径: hdfs://node01:8020/xxx
            System.out.println(fileStatus.getPath()+"----"+fileStatus.getPath().getName());
            // 文件的block信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println("block数:"+blockLocations.length);

        }
    }


    /*
  获取FileSystem方式4
   */
    @Test
    public void getFileSystem4() throws URISyntaxException, IOException {
     FileSystem fileSystem = FileSystem.newInstance(new URI("hdfs://node01:8020"), new Configuration());

        System.out.println(fileSystem);
    }

    /*
   获取FileSystem方式3
    */
    @Test
    public void getFileSystem3() throws IOException {

        Configuration configuration = new Configuration();
        // 指定文件系统类型
          configuration.set("fs.defaultFS","hdfs://node01:8020");
        // 获取指定的文件系统
        FileSystem fileSystem = FileSystem.newInstance(configuration);

        System.out.println(fileSystem);
    }

    /*
   获取FileSystem方式2
    */
    @Test
    public void getFileSystem2() throws URISyntaxException, IOException {
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());

        System.out.println(fileSystem);
    }

    /*
    获取FileSystem方式1
     */
    @Test
    public void getFileSystem1() throws IOException {
        // 1.创建Configuration对象
        Configuration configuration = new Configuration();
        // 2.设置文件系统的类型
        configuration.set("fs.defaultFS","hdfs://node01:8020");
        // 3.获取指定的文件系统
        FileSystem fileSystem = FileSystem.get(configuration);
        // 4.输出
        System.out.println(fileSystem);
    }


    /*
    使用url方式访问数据
     */
   @Test
    public void urlHdfs() throws IOException {

       // 1.注册url
       URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
       // 2.获取hdfs文件的输入流
       InputStream inputStream = new URL("hdfs://node01:8020/a.txt").openStream();
       // 3.获取本地文件的输出流
       FileOutputStream outputStream = new FileOutputStream("D:\\hello.txt");
       // 4.实现文件的拷贝
       IOUtils.copy(inputStream,outputStream);
       // 5.关闭流
       IOUtils.closeQuietly(inputStream);
       IOUtils.closeQuietly(outputStream);
    }
}