package cn.itcast.controller;

import cn.itcast.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*

 */
@Controller
@RequestMapping("/User")
public class UserController {

    /*
    返回String
     */
    @RequestMapping("/testString")
    public String testString(Model model) {
        System.out.println("testString方法执行了...!");
        // 模拟从数据库中查询出User对象
        User user = new User();
        user.setUsername("美美");
        user.setPassword("123");
        user.setAge(30);
        // model对象
        model.addAttribute("user", user);
        return "seccess";

    }

    /*
    void

     */
    @RequestMapping("/testVoid")
    public void testVoid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("testVoid方法执行了...!");
        // response编写请求转发程序
        // request.getRequestDispatcher("/WEB-INF/pages/seccess.jsp").forward(request,response);

        // 重定向
        // response.sendRedirect(request.getContextPath()+"/index.jsp");

        // 设置中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 直接会进行响应
        response.getWriter().print("hello");

        return;
    }


    /*
    返回String
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(Model model) {
        // 创建ModelAndView对象
        ModelAndView mv = new ModelAndView();
        System.out.println("testModelAndView方法执行了...!");
        // 模拟从数据库中查询出User对象
        User user = new User();
        user.setUsername("小风");
        user.setPassword("456");
        user.setAge(30);
        // 把user对象存储到mv对象中,也会把user对象存入到request对象中
        mv.addObject("user", user);
        //跳转到哪个界面
        mv.setViewName("seccess");
        return mv;

    }

    /*
    使用关键字的方式进行转发或者重定向
     */
    @RequestMapping("/testForwardOrRedirect")
    public String testForwardOrRedirect() {
        System.out.println("testForwardOrRedirect方法执行了...!");

        // 用字符串关键字请求转发
        // return "forward:/WEB-INF/pages/seccess.jsp";

        // 重定向
        return "redirect:/index.jsp";
    }

    /*
   模拟异步请求响应
    */
//    @RequestMapping("/testAjax")
//    public void testAjax(@RequestBody String body) {
//        System.out.println("testAjax方法执行了...!");
//        System.out.println(body);
//    }

    @RequestMapping("/testAjax")
    public @ResponseBody User testAjax(@RequestBody User user) {
        System.out.println("testAjax方法执行了...!");
        // 客户端发送ajax的请求,传的是json字符串,后端把json字符串封装到user对象中
        System.out.println(user);
        // 作响应,模拟数据库
        user.setUsername("haha");
        user.setPassword("123");
        user.setAge(20);
        // 作响应
        return user ;
    }
}