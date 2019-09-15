package com.pinyougou.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDFSClient;

@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String file_server_url ;    //来自application.properties.文件中

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {
            // 获得文件全名称
            String originalFilename = file.getOriginalFilename() ;
            // 获取扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1) ;
        try {
            // 获取客户端
            FastDFSClient client = new FastDFSClient("classpath:config/fdfs_client.conf");
            // 获得部分地址
            String fileId =  client.uploadFile(file.getBytes(), extName);
            // 获取图片完整地址
            String url = file_server_url+fileId ;
            return new Result(true, url) ;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败") ;
        }

    }

}