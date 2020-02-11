package cn.allandeng.blog.controller.admin;

import cn.allandeng.blog.domain.Blog;
import cn.allandeng.blog.domain.Type;
import cn.allandeng.blog.domain.User;
import cn.allandeng.blog.service.BlogService;
import cn.allandeng.blog.service.TagService;
import cn.allandeng.blog.service.TypeService;
import cn.allandeng.blog.vo.BlogQuery;
import org.springframework.beans.factory.NamedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
* @ClassName BlogController 
* @Date: 2020/2/5 14:34 
* @Description: 博客控制器
* @Author: Allan Deng
* @Version: 1.0 
**/
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /*
     * @Title blogs
     * @Author Allan Deng
     * @Description 博客管理页面控制器
     * @Date 15:01 2020/2/5
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8 ,sort = {"createTime"} , direction = Sort.Direction.DESC) Pageable pageable ,
                        BlogQuery blog , Model model) {
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        model.addAttribute("types",typeService.listType());
        return LIST;
    }

//    @PostMapping("/blogs/search")
//    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
//                         BlogQuery blog, Model model) {
//        model.addAttribute("page", blogService.listBlog(pageable, blog));
//        return "admin/blogs :: blogList";//仅仅返回片段
//    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        //向前端传一个模型，不然会疯狂报错。。。
        model.addAttribute("blog",new Blog());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String update(@PathVariable(name = "id") Long id , Model model){
        //向前端传一个模型，不然会疯狂报错。。。
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;

    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id , RedirectAttributes attributes){
        Blog blog = blogService.getBlog(id);
        if (blog != null){
            blogService.deleteBlog(id);
            attributes.addFlashAttribute("message", "操作成功");
        }else{
            attributes.addFlashAttribute("message", "操作失败");
        }
        return REDIRECT_LIST;

    }


    @PostMapping("/blogs")
    public String post(Blog blog , RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        //id再前端页面中为隐藏域，会传值回来，但是还有很多属性前端页面中不修改。如果不进行update操作，会出现部分属性为null的情况
        if(blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else{
            //更新操作，会进行属性复制
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }
}
