package cn.allandeng.blog.controller.admin;/**
 * @Auther: Allan
 * @Date: 2020/2/5 15:27
 * @Description:
 */

import cn.allandeng.blog.domain.Tag;
import cn.allandeng.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @ClassName TagController
 * @Date:2020/2/5 15:27
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;
    
    /*
     * @Title tags
     * @Author Allan Deng
     * @Description tags控制器，查询标签，按照id降序，并返回查询结果到前端页面
     * @Date 15:37 2020/2/5
     * @Param [pageable, model]
     * @return java.lang.String
     **/
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10 , sort = {"id"} ,direction = Sort.Direction.DESC)
                                    Pageable pageable,
                        Model model){
        model.addAttribute("page" , tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag-input";
    }

    /*
     * @Title post
     * @Author Allan Deng
     * @Description 添加新的标签
     * @Date 20:10 2020/2/5
     * @Param [tag, result, attributes]
     * @return java.lang.String
     **/
    @PostMapping("/tags")
    public String post(@Valid Tag tag , BindingResult result, RedirectAttributes attributes){
        //校验是否重复
        if(tagService.getTagByName(tag.getName()) != null){
            result.rejectValue("name" , "nameError" ,"该标签已存在，不能重复添加！");
            return "admin/tag-input";
        }

        //校验错误
        if (result.hasErrors()){
            //不用添加Attribute，前面的页面返回了一个model其中包含错误信息
            //attributes.addFlashAttribute("message",result.);
            return "admin/tag-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            //保存失败
            attributes.addFlashAttribute("message","操作失败");
        }else{
            //保存成功
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }

    /*
     * @Title updataPost
     * @Author Allan Deng
     * @Description 更新标签
     * @Date 20:11 2020/2/5
     * @Param [tag, result, id, attributes]
     * @return java.lang.String
     **/
    //为了成功使用校验，BindingResult需要紧跟在@Valid的参数后
    @PostMapping("/tags/{id}")
    public String updataPost(@Valid Tag tag,
                             BindingResult result,
                             @PathVariable Long id,
                             RedirectAttributes attributes) {
        //验证是否重复
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        //校验错误
        if (result.hasErrors()) {
            return "admin/tag-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        //向前端传一个tag对象，用于验证
        model.addAttribute("tag",new Tag());
        return "admin/tag-input";
    }



    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id , RedirectAttributes attributes){
        System.out.println(id);
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
