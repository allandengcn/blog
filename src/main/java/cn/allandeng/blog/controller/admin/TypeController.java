package cn.allandeng.blog.controller.admin;/**
 * @Auther: Allan
 * @Date: 2020/2/5 15:27
 * @Description:
 */

import cn.allandeng.blog.domain.Type;
import cn.allandeng.blog.service.TypeService;
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
 * @ClassName TypeController
 * @Date:2020/2/5 15:27
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;
    
    /*
     * @Title types
     * @Author Allan Deng
     * @Description types控制器，查询分类，按照id降序，并返回查询结果到前端页面 
     * @Date 15:37 2020/2/5
     * @Param [pageable, model]
     * @return java.lang.String
     **/
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10 , sort = {"id"} ,direction = Sort.Direction.DESC)
                                    Pageable pageable,
                        Model model){
        model.addAttribute("page" , typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";
    }

    /*
     * @Title post
     * @Author Allan Deng
     * @Description 添加新的分类
     * @Date 20:10 2020/2/5
     * @Param [type, result, attributes]
     * @return java.lang.String
     **/
    @PostMapping("/types")
    public String post(@Valid Type type , BindingResult result, RedirectAttributes attributes){
        //校验是否重复
        if(typeService.getTypeByName(type.getName()) != null){
            result.rejectValue("name" , "nameError" ,"该分类已存在，不能重复添加！");
            return "admin/type-input";
        }

        //校验错误
        if (result.hasErrors()){
            //不用添加Attribute，前面的页面返回了一个model其中包含错误信息
            //attributes.addFlashAttribute("message",result.);
            return "admin/type-input";
        }
        Type t = typeService.saveType(type);
        if(t == null){
            //保存失败
            attributes.addFlashAttribute("message","操作失败");
        }else{
            //保存成功
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }

    /*
     * @Title updataPost
     * @Author Allan Deng
     * @Description 更新分类
     * @Date 20:11 2020/2/5
     * @Param [type, result, id, attributes]
     * @return java.lang.String
     **/
    //为了成功使用校验，BindingResult需要紧跟在@Valid的参数后
    @PostMapping("/types/{id}")
    public String updataPost(@Valid Type type,
                             BindingResult result,
                             @PathVariable Long id,
                             RedirectAttributes attributes) {
        //验证是否重复
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        //校验错误
        if (result.hasErrors()) {
            return "admin/type-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        //向前端传一个type对象，用于验证
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }



    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id , RedirectAttributes attributes){
        System.out.println(id);
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
