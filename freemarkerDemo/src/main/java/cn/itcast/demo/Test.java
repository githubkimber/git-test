package cn.itcast.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Test {

    public static void main(String[] args) throws Exception{

        // 1.创建一个配置对象
        Configuration configuration = new Configuration(Configuration.getVersion()) ;
        // 2.设置模板所在的目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\develop\\idea_workspace\\freemarkerDemo\\src\\main\\resources"));
        // 3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        // 4.获取模板对象
        Template template = configuration.getTemplate("test.ftl");
        // 5.创建数据模型(可以是对象,也可以是map)
        Map map = new HashMap();
        map.put("name","张三");
        map.put("message","欢迎来到品优购!!!");
        map.put("success",true);

        List goodsList=new ArrayList();
        Map goods1=new HashMap();
        goods1.put("name", "苹果");
        goods1.put("price", 5.8);
        Map goods2=new HashMap();
        goods2.put("name", "香蕉");
        goods2.put("price", 2.5);
        Map goods3=new HashMap();
        goods3.put("name", "橘子");
        goods3.put("price", 3.2);
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        map.put("goodsList", goodsList);

        map.put("today",new Date());

        map.put("point",165131356);

        // 6.创建一个输出流对象
        Writer out = new FileWriter("D:\\test.html");
        // 7.输出     将数据对象map输出到输出流out的test.html文件中;
        template.process(map,out);
        // 8.关闭out输出流
        out.close();
    }
}
