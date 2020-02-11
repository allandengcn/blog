package cn.allandeng.blog.handler;
/**
 * @Auther: Allan
 * @Date: 2020/2/4 16:46
 * @Description:
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ControllerExceptHandler
 * @Date:2020/2/4 16:46
 * @Description: 统一异常处理
 * @Author: Allan Deng
 * @Version: 1.0
 **/
//拦截所有Controller
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Title exceptionHandler
     * @Author Allan Deng
     * @Description  异常处理方法，拦截所有runtime异常，输出日志，跳转到错误页面
     * @Date 12:00 2020/2/10
     * @Param [request, e]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    //拦截所有异常
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {} , Exception : {}" , request.getRequestURL() ,e);

        //判断是否有错误状态的注解，需要传入类型和注解
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        //携带错误信息，跳转到错误页面
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("error",e);
        mv.setViewName("error/error");
        return mv;
    }
}

