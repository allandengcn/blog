package cn.allandeng.blog.controller;/**
 * @Auther: Allan
 * @Date: 2020/2/9 13:06
 * @Description:
 */

import cn.allandeng.blog.util.CaptchaUtil;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName CaptchaController
 * @Date:2020/2/9 13:06
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Controller
public class CaptchaController {

    //评论页面获得验证码
    @GetMapping("/captcha")
    public String getCaptcha(HttpSession session , Model model) throws Exception {
        Map<String, String> captcha = CaptchaUtil.getCaptcha(4);
        if (session.getAttribute("captcha") != null ){
            session.removeAttribute("captcha");
            //model.addAttribute("message" , "验证码错误！");
        }
        session.setAttribute("captcha" , captcha.get("str"));
        model.addAttribute("captcha",captcha.get("image"));
        return "blog :: captcha-Img";
    }

    //登陆页面获得验证码
    @GetMapping("/captchalogin")
    public String getCaptchaLogin(HttpSession session , Model model) throws Exception {
        Map<String, String> captcha = CaptchaUtil.getCaptcha(4);
        if (session.getAttribute("captchaLogin") != null ){
            session.removeAttribute("captchaLogin");
            //model.addAttribute("message" , "验证码错误！");
        }
        session.setAttribute("captchaLogin" , captcha.get("str"));
        model.addAttribute("captchaLogin",captcha.get("image"));
        return "admin/login :: captcha-Img";
    }


}
