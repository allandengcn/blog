package cn.allandeng.blog.controller;

import cn.allandeng.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
* @ClassName ArchiveShowController
* @Date: 2020/2/10 13:10
* @Description:
* @Author: Allan Deng
* @Version: 1.0
**/
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    /**
     * @Title archives
     * @Author Allan Deng
     * @Description  显示归档页面
     * @Date 13:10 2020/2/10
     * @Param [model]
     * @return java.lang.String
     **/
    @GetMapping("/archives")
    public String archives(Model model) {

        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
