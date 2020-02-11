package cn.allandeng.blog.controller;/**
 * @Auther: Allan
 * @Date: 2020/2/7 17:58
 * @Description:
 */

import cn.allandeng.blog.domain.Comment;
import cn.allandeng.blog.domain.User;
import cn.allandeng.blog.service.BlogService;
import cn.allandeng.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ClassName CommentController
 * @Date:2020/2/7 17:58
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
    * @ClassName CommentController 
    * @Date: 2020/2/10 13:11 
    * @Description: 获取博客的所有评论
    * @Author: Allan Deng
    * @Version: 1.0 
    **/
    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId , Model model, HttpSession session){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("admin",true);
        }
        return "blog :: commentList";
    }

    
    /**
     * @Title post
     * @Author Allan Deng
     * @Description 添加评论 
     * @Date 13:59 2020/2/10
     * @Param [comment, session, attributes]
     * @return java.lang.String
     **/
    //拿到session用来确定用户身份，为管理员时在前端添加标签,在提交时使用
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session , RedirectAttributes attributes) {
        String captcha = (String) session.getAttribute("captcha");
        Long blogId = comment.getBlog().getId();
        if(captcha==null || comment.getCaptchacode()==null ||!comment.getCaptchacode().equalsIgnoreCase(captcha)){
            //验证失败
            attributes.addFlashAttribute("message" , "验证码错误,请点击验证码刷新");
            return "redirect:/comments/" + blogId ;
        }else {
            //验证通过
            comment.setBlog(blogService.getBlog(blogId));
            User user = (User) session.getAttribute("user");
            session.removeAttribute("captcha");
            if (user != null) {
                comment.setAvatar(user.getAvatar());
                comment.setAdminComment(true);
            } else {
                comment.setAvatar(avatar);
            }
            commentService.saveComment(comment);
            return "redirect:/comments/" + blogId;
        }

    }
    
    /**
     * @Title delete
     * @Author Allan Deng
     * @Description 删除评论 
     * @Date 13:11 2020/2/10
     * @Param [id]
     * @return java.lang.String
     **/
    @GetMapping("/comments/delete/{id}")
    public String delete(@PathVariable Long id ){
        Comment comment = commentService.getComment(id);
        Long blogId = comment.getBlog().getId();
        commentService.deleteComment(id);
        return "redirect:/blog/" + blogId+"#comment-container";

    }
}
