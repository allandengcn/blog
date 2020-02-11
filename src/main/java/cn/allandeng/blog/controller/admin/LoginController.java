package cn.allandeng.blog.controller.admin;/**
 * @Auther: Allan
 * @Date: 2020/2/5 12:41
 * @Description:
 */

import cn.allandeng.blog.domain.User;
import cn.allandeng.blog.service.UserService;
import cn.allandeng.blog.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName LoginController
 * @Date:2020/2/5 12:41
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    /*
     * @Title loginPage
     * @Author Allan Deng
     * @Description  登陆页面
     * @Date 12:50 2020/2/5
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping
    public String loginPage(Model model ,HttpSession session) throws Exception {
        //添加验证码
        Map<String, String> captcha = CaptchaUtil.getCaptcha(4);
        if (session.getAttribute("captchaLogin") != null ){
            session.removeAttribute("captchaLogin");
        }
        session.setAttribute("captchaLogin" , captcha.get("str"));
        model.addAttribute("captchaLogin",captcha.get("image"));
        return "admin/login";
    }

    /*
     * @Title login
     * @Author Allan Deng
     * @Description 登陆
     * @Date 12:50 2020/2/5
     * @Param [username, password, session, attributes]
     * @return java.lang.String
     **/
    @PostMapping("/login")
    public String login(@RequestParam String username ,
                        @RequestParam String password ,
                        @RequestParam String captchacode ,
                        HttpSession session,
                        RedirectAttributes attributes){
        String captcha = (String) session.getAttribute("captchaLogin");
        if(captchacode==null || captcha == null || !captchacode.equalsIgnoreCase(captcha)){
            //返回信息给页面
            attributes.addFlashAttribute("message" , "验证码错误");
            return "redirect:/admin" ;
        }
        User user = userService.checkUser(username,password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index" ;
        }else{
            //返回信息给页面
            attributes.addFlashAttribute("message" , "用户名或密码错误");
            return "redirect:/admin" ;
        }
    }

    /*
     * @Title logout
     * @Author Allan Deng
     * @Description 登出，清空session中的user
     * @Date 12:54 2020/2/5
     * @Param [session]
     * @return java.lang.String
     **/
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");

        return "redirect:/admin";
    }
}
