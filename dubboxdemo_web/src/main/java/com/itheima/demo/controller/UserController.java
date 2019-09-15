package com.itheima.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itheima.demo.service.UserService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/User")
public class UserController {

    @Reference      // @Reference: 远程注入,不是本地;
    private UserService userService ;

    @RequestMapping("/showName")
    @ResponseBody  // 不加@ResponseBody返回的是跳转页面,加上后返回的就是输出值.
    public String showName(){
        return  userService.getName();
    }
}
/*    @ResponseBody是作用在方法上的，@ResponseBody 表示该方法的返回结果
直接写入 HTTP response body 中，一般在异步获取数据时使用【也就是AJAX】，
在使用 @RequestMapping后，返回值通常解析为跳转路径，但是加上 @ResponseBody
后返回结果不会被解析为跳转路径，而是直接写入 HTTP response body 中。
比如异步获取 json 数据，加上 @ResponseBody 后，会直接返回 json 数据。
@RequestBody 将 HTTP 请求正文插入方法中，使用适合的 HttpMessageConverter
将请求体写入某个对象。
*/