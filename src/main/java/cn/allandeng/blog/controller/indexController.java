package cn.allandeng.blog.controller;/**
 * @Auther: Allan
 * @Date: 2020/2/4 16:35
 * @Description:
 */

import cn.allandeng.blog.domain.User;
import cn.allandeng.blog.exception.NotFoundException;
import cn.allandeng.blog.service.BlogService;
import cn.allandeng.blog.service.CommentService;
import cn.allandeng.blog.service.TagService;
import cn.allandeng.blog.service.TypeService;
import cn.allandeng.blog.util.CaptchaUtil;
import cn.allandeng.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName indexController
 * @Date:2020/2/4 16:35
 * @Description: 首页控制器
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Controller
public class indexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    /**
     * @Title index
     * @Author Allan Deng
     * @Description 首页
     * @Date 13:17 2020/2/10
     * @Param [pageable, model]
     * @return java.lang.String
     **/
    @GetMapping("/")
    public String index(@PageableDefault(size = 8 ,sort = {"createTime"} , direction = Sort.Direction.DESC) Pageable pageable ,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        //model.addAttribute("page", blogService.listBlogPublished(pageable));
        model.addAttribute("types" , typeService.listTypeTop(6));
        model.addAttribute( "tags" , tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }

    /**
     * @Title search
     * @Author Allan Deng
     * @Description 搜索
     * @Date 13:18 2020/2/10
     * @Param [query, pageable, model]
     * @return java.lang.String
     **/
    @PostMapping("/search")
    public String search(@RequestParam String query,
            @PageableDefault(size = 8 ,sort = {"createTime"} , direction = Sort.Direction.DESC) Pageable pageable ,
             Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    /**
     * @Title blog
     * @Author Allan Deng
     * @Description 博客详情
     * @Date 13:18 2020/2/10
     * @Param [id, model, session]
     * @return java.lang.String
     **/
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model , HttpSession session) throws Exception {
        model.addAttribute("blog",blogService.getAndConvertBlog(id));
        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        Map<String, String> captcha = CaptchaUtil.getCaptcha(4);
        if (session.getAttribute("captcha") != null ){
            session.removeAttribute("captcha");
        }
        session.setAttribute("captcha" , captcha.get("str"));
        model.addAttribute("captcha",captcha.get("image"));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("admin",true);
        }
        return "blog";
    }

    /**
     * @Title newblogs
     * @Author Allan Deng
     * @Description footer中最新博客
     * @Date 13:18 2020/2/10
     * @Param [model]
     * @return java.lang.String
     **/
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

}
