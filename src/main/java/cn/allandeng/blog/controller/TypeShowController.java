package cn.allandeng.blog.controller;

import cn.allandeng.blog.domain.Type;
import cn.allandeng.blog.service.BlogService;
import cn.allandeng.blog.service.TypeService;
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
* @ClassName TypeShowController 
* @Date: 2020/2/10 13:19 
* @Description: 分类
* @Author: Allan Deng
* @Version: 1.0 
**/
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;
    
    /**
     * @Title types
     * @Author Allan Deng
     * @Description 获取分类 
     * @Date 14:21 2020/2/10
     * @Param [pageable, id, model]
     * @return java.lang.String
     **/
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
           id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
