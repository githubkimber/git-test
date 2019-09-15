package cn.itcast.controller;

import cn.itcast.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;
import java.util.Map;

/*
常用注解
 */
@Controller
@RequestMapping("/anno")
@SessionAttributes(value = {"msg"})     // 把msg=美美存入到session域对象中
public class AnnoController {

    @RequestMapping("/testRequestParam")
    public String testRequestParam(@RequestParam(name="/name") String username){
        System.out.println("testRequestParam执行了...!");
        System.out.println(username);
        return "success" ;
    }

    /*
    获取到请求体的内容
     */
    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String jgbj){
        System.out.println("testRequestBody执行了...!");
        System.out.println(jgbj);
        return "success" ;
    }

    /*
    PathVariable注解
   */
    @RequestMapping(value = "/testPathVariable/{sid}",method = RequestMethod.PUT)
    public String testPathVariable(@PathVariable(name = "sid") String id){
        System.out.println("testPathVariable执行了...!");
        System.out.println(id);
        return "success" ;
    }

    /*
    @RequestHeader(value = "Accept")
    获取请求头的值
   */
    @RequestMapping(value = "/testRequestHaeder")
    public String testRequestHaeder(@RequestHeader(value = "Accept") String header){
        System.out.println("testRequestHaeder执行了...!");
        System.out.println(header);
        return "success" ;
    }

    /*
    @RequestHeader(value = "CookieValue")
    获取cookie的值
   */
    @RequestMapping(value = "/testCookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID") String cookieValue){
        System.out.println("testCookieValue执行了...!");
        System.out.println(cookieValue);
        return "success" ;
    }


    /*
    ModelAttribute注解
   */
    @RequestMapping(value = "/testModelAttribute")
    public String testModelAttribute(@ModelAttribute("abc") User user){
        System.out.println("testModelAttribute执行了...!");
        System.out.println(user);
        return "success" ;
    }

    /*
    该方法会先执行String uname , Map<String,User> map
     */
    @ModelAttribute
    public void showUser(String uname , Map<String,User> map){
        System.out.println("showUser执行了map...!");
        // 通过用户查询数据库(模拟)
        User user = new User() ;
        user.setUanme(uname);
        user.setAge(20);
        user.setDate(new Date());
        map.put("abc" , user) ;
    }

    /*
    该方法会先执行
     */
    @ModelAttribute
    public User showUser(String uname){
        System.out.println("showUser执行了...!");
        // 通过用户查询数据库(模拟)
        User user = new User() ;
        user.setUanme("2");
        user.setAge(25);
        user.setDate(new Date());
        return user ;
    }

    /*
    SessionAttributes的注解    @SessionAttribute("abc") User user
   */
    @RequestMapping(value = "/testSessionAttributes")
    public String testSessionAttributes(Model model){
        System.out.println("testSessionAttributes执行了...!");
        // 底层会存储到request域对象中
        model.addAttribute("msg","美美") ;
        return "success" ;
    }

    /*
    获取值
     */
    @RequestMapping(value = "/getSessionAttributes")
    public String getSessionAttributes(ModelMap modelMap){
        System.out.println("getSessionAttributes...!");
        String msg = (String) modelMap.get("msg") ;
        System.out.println(msg) ;
        return "success" ;
    }

    /*
   清除值
    */
    @RequestMapping(value = "/deleteSessionAttributes")
    public String deleteSessionAttributes(SessionStatus status){
        System.out.println("deleteSessionAttributes...!");
        status.setComplete() ;
        return "success" ;
    }

    /*
    删除值(自己写的没有删除key和值的功能)
     */
//    @RequestMapping(value = "/deleteSessionAttributes")
//    public String deleteSessionAttributes(ModelMap modelMap){
//        System.out.println("deleteSessionAttributes...!");
//        String msg = (String) modelMap.remove("msg") ;
//        System.out.println(modelMap) ;
//        return "success" ;
//    }
}
