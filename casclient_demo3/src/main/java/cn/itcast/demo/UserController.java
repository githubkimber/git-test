package cn.itcast.demo;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // 用来读取当前用户名
    @RequestMapping("/findLoginUser")
    public void findLogUser(){

        // 当前登录名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("当前登录名"+name);
    }
}