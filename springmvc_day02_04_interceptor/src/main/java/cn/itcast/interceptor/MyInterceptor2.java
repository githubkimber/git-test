package cn.itcast.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
自定义拦截器
 */
public class MyInterceptor2 implements HandlerInterceptor{

    /*
    预处理,  Controller方法执行前
    return turn 放行, 执行下一个拦截器, 如果没有, 执行controller中的方法
    return false 不放行
     *///                                       请求                          响应
    public boolean preHandle(HttpServletRequest request , HttpServletResponse response, Object handler)throws Exception{
        System.out.println("自定义拦截器MyInterceptor2执行了...!");
        // 跳转页面(请求转发)
        //request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
        return true ;   // true 放行; false 不放行;
    }

    /*
    后处理方法: 在controller方法执行后,success.jsp执行之前执行;
     */
    public void postHandle(HttpServletRequest request , HttpServletResponse response, Object handler,ModelAndView modelAndView)throws Exception{
        System.out.println("自定义拦截器MyInterceptor2执行了...后处理!");
        // 跳转页面(请求转发)
        // request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);

    }

    /*
    success.jsp页面执行后,该方法才执行
     */
    public void afterCompletion(HttpServletRequest request , HttpServletResponse response, Object handler,Exception ex)throws Exception{
        System.out.println("自定义拦截器MyInterceptor2执行了...最后!");

    }
}
