package cn.itcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 控制器类
@Controller
@RequestMapping(path = "/user")
public class HelloController {

    @RequestMapping(path="/hello") // 请求的一个映射
    public String sayHello(){
        System.out.println("Hello StringMVC");
        return "success";
    }

    /*
    RequestMapping注解
     */
//    @RequestMapping(value="/testRequrstMapping",method={RequestMethod.GET},params = {"username=haha"},headers = {"Accept"})
    @RequestMapping(value="/testRequrstMapping")
    public String testRequestMapping(){
        System.out.println("测试RequestMapping注解...!");
        return "success" ;
    }
}
