package cn.allandeng.blog.aspect;
/**
 * @Auther: Allan
 * @Date: 2020/2/4 17:30
 * @Description:
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @ClassName LogAspect
 * @Date:2020/2/4 17:30
 * @Description: 日志切面
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * 切入点，所有的web控制器
     * 第一个*，表示所有的修饰符的方法和类（private public protected default）
     * 第二个*，表示包下所有类
     * 第三个*，表示类中所有方法
     * 结尾(..)，表示所有参数的方法
     */
    /**
     * @Title log
     * @Author Allan Deng
     * @Description  切入点
     * @Date 19:02 2020/2/4
     * @Param []
     * @return void
     **/
    @Pointcut("execution(* cn.allandeng.blog.controller.*.*(..))")
    public void log(){}

    /**
     * @Title doBefore
     * @Author Allan Deng
     * @Description  切入点之前的操作
     * @Date 19:02 2020/2/4
     * @Param [joinPoint]
     * @return void
     **/
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //请求之前记录 url ip 调用方法 传递的参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog log = new RequestLog(url , ip , classMethod , args);

        logger.info("Request : {}" , log);
    }

//    切入点之后暂时无操作
//    @After("log()")
//    public void doAfter(){
//        logger.info("---After---");
//    }

//    返回值无操作
//    @AfterReturning(pointcut = "log()" , returning = "result")
//    public void doAfterReturn(Object result){
//        logger.info("return result:{}",result);
//    }

    /**
    * @ClassName RequestLog
    * @Date: 2020/2/4 19:03
    * @Description: 内部类，记录请求信息
    * @Author: Allan Deng
    * @Version: 1.0
    **/
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
