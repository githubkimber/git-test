package cn.itcast.controller;

import cn.itcast.domain.Account;
import cn.itcast.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
强求参数绑定
 */
@Controller
@RequestMapping("/param")
public class ParamController {
    /*
    请求参数的帮定
     */
    @RequestMapping("/testParam")
    public String testParam(String username,int password){
        System.out.println("执行了....!");
        System.out.println("用户名:"+username);
        System.out.println("密码:"+password);
        return "success" ;
    }

    /*
    请求参数绑定把数据封装到JavaBean的类中
     */
    @RequestMapping("/saveAccount")
    public String saveAccount(Account account){
        System.out.println("saveAccount执行了");
        System.out.println(account);
        return "success" ;
    }

    /*
   请求参数绑定把数据封装到自定义类型转换器中
    */
    @RequestMapping("/saveUser")
    public String saveUser(User user) {
        System.out.println("saveUser执行了");
        System.out.println(user);
        return "success";
    }
         /*
   请求参数绑定把数据封装到原生的API中
    */
        @RequestMapping("/testServlet")
        public String testServlet(HttpServletRequest request , HttpServletResponse response){
            System.out.println("testServlet执行了...!");
            System.out.println(request);

            HttpSession session = request.getSession() ;
            System.out.println(session);

            ServletContext servletContext = session.getServletContext() ;
            System.out.println(servletContext);

            System.out.println(response);
            return "success" ;
    }
}
