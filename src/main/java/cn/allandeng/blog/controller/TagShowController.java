package cn.allandeng.blog.controller;

import cn.allandeng.blog.domain.Tag;
import cn.allandeng.blog.service.BlogService;
import cn.allandeng.blog.service.TagService;
import cn.allandeng.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
* @ClassName TagShowController 
* @Date: 2020/2/10 13:18 
* @Description: 标签
* @Author: Allan Deng
* @Version: 1.0 
**/
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;
    
    /**
     * @Title tags
     * @Author Allan Deng
     * @Description 获取标签 
     * @Date 14:00 2020/2/10
     * @Param [pageable, id, model]
     * @return java.lang.String
     **/
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
           id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
