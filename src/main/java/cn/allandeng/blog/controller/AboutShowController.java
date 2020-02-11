package cn.allandeng.blog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @ClassName AboutShowController
* @Date: 2020/2/10 13:09
* @Description: About的Controller
 * @Author: Allan Deng
* @Version: 1.0
**/
@Controller
public class AboutShowController {


    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
