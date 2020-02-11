package cn.allandeng.blog.interceptor;
/**
 * @Auther: Allan
 * @Date: 2020/2/5 14:42
 * @Description:
 */

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Date:2020/2/5 14:42
 * @Description: 登陆拦截器
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * @Title preHandle
     * @Author Allan Deng
     * @Description 预处理 在请求为到达之前的操作,拦截未登陆的操作
     * @Date 14:43 2020/2/5
     * @Param [request, response, handler]
     * @return boolean
     **/
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
