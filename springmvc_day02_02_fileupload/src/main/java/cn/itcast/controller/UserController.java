package cn.itcast.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    /*
    传统文件上传
     */
    @RequestMapping("/fileuploadl")
    public String fileuploadl(HttpServletRequest request) throws Exception {
        System.out.println("传统文件上传...!");

        // 使用fileUpload组建完成文件上传
        // 上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/") ;
        // 判断该路径是否存在
        File file = new File(path);
        if (!file.exists()){
            // 创建该文件夹
            file.mkdirs();
        }
        // 输出文件的绝对路径
        System.out.println(file.getAbsoluteFile());
        // 解析request对象, 获取上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory() ;
        ServletFileUpload upload = new ServletFileUpload(factory) ;
        // 解析resquest
        List<FileItem> items = upload.parseRequest(request) ;
        // 遍历
        for (FileItem item:items){
            // 进行判断, 当前item对象是否是上传文件项
            if (item.isFormField()){
                // 说明普通表单项

           }else{
                // 说明文件上传项
                //  获取上传文件的名称
                String filename =item.getName() ;
                // 把文件的名称设置成唯一值:uuid 防止覆盖
                String uuid = UUID.randomUUID().toString().replace("-" , "") ;
                filename = uuid + "_"+filename ;
                //完成文件上传
                item.write(new File(path , filename)) ;
                // 删除临时文件
                item.delete();
            }
        }
        return "success" ;
    }

    /*
  springmvc文件上传
   */
    @RequestMapping("/fileupload2")
    public String fileupload2(HttpServletRequest request , MultipartFile upload) throws Exception {
        System.out.println("springmvc文件上传...!");

        // 使用fileUpload组建完成文件上传
        // 上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        // 判断该路径是否存在
        File file = new File(path);
        if (!file.exists()) {
            // 创建该文件夹
            file.mkdirs();
        }
        // 输出文件的绝对路径
        System.out.println(file.getAbsoluteFile());

        // 说明文件上传项
        //  获取上传文件的名称
        String filename = upload.getOriginalFilename();
        // 把文件的名称设置成唯一值:uuid 防止覆盖
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + "_" + filename;
        //完成文件上传
        upload.transferTo(new File(path, filename));

        return "success";
    }

    /*
   springmvc跨服务器文件上传
   */
    @RequestMapping("/fileupload3")                                 //   文件项
    public String fileupload3(MultipartFile upload) throws Exception {
        System.out.println("springmvc跨服务器文件上传...!");

        // 定义上传文件服务器路径
        String path = "http://localhost:8080/uploads/";

        // 输出文件的绝对路径
        // System.out.println(path.getAbsoluteFile());

        // 说明文件上传项
        //  获取上传文件的名称
        String filename = upload.getOriginalFilename();
        // 把文件的名称设置成唯一值:uuid 防止覆盖
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + "_" + filename;

        // 创建客户端的对象
        Client client = Client.create() ;
        // 和图片服务器进行连接
        WebResource webResource = client.resource(path+filename) ;
        //完成文件上传
        webResource.put(upload.getBytes());

        return "success";
    }
}
