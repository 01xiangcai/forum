package com.yao.forum.Controller;

import com.yao.forum.dto.PaginationDTO;
import com.yao.forum.mapper.UserMapper;
import com.yao.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
                        Model model,
                        RedirectAttributesModelMap modelMap,
                        @RequestParam(name = "page" ,defaultValue = "1") Integer page,
                        @RequestParam(name = "size" ,defaultValue = "10") Integer size,
                        @RequestParam(name = "search" ,required = false) String search
                        ) {

        PaginationDTO pagination = questionService.list(search,page,size);
        if (pagination==null){
            modelMap.addFlashAttribute("error","查找的问题不存在");
            return "redirect:/";
        }
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        return "index";
    }
}
