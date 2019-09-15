package cn.itcast.exception;

import cn.itcast.exception.SysException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
异常处理器
 */
public class SysExceptionResolver implements HandlerExceptionResolver {
    /*
    处理异常业务逻辑
     */                                                     // 请求                       响应     当前处理器的对象     异常对象
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 获取异常对象
        SysException e = null ;
        // instanceof用于判断是否为同一类型
        if (ex instanceof SysException){
            e = (SysException)ex ;
        }
        else{
            e = new SysException("系统正在维护...!") ;
        }
        // 创建ModelAndView对象
        ModelAndView mv = new ModelAndView() ;
        mv.addObject("errorMsg",e.getMessage()) ;
        mv.setViewName("error");
        return mv;
    }
}
