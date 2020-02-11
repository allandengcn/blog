package cn.allandeng.blog.interceptor;
/**
 * @Auther: Allan
 * @Date: 2020/2/5 14:49
 * @Description:
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName WebConfig
 * @Date:2020/2/5 14:49
 * @Description: web配置类
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * @Title addInterceptors
     * @Author Allan Deng
     * @Description 添加拦截器的拦截规则
     * @Date 14:54 2020/2/5
     * @Param [registry]
     * @return void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**") //拦截所有的admin下的路径
                .addPathPatterns("/comments/delete/**")//拦截评论删除操作
                .excludePathPatterns("/admin") //忽略admin（登陆页面）
                .excludePathPatterns("/admin/login");//忽略admin/login（登陆表单提交页面）

    }
}
