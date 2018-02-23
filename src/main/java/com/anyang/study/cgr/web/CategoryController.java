package com.anyang.study.cgr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

    @GetMapping("/categoryList")
    public String selectCategoryList(Model model) {
//        CategoryVO categoryVO =

        return null;
    }


}
