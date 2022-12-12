package com.yao.forum.Controller;

import com.yao.forum.dto.QuestionDTO;
import com.yao.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String quetions(@PathVariable(name = "id") Integer id,
                           Model model){

        QuestionDTO questionDTO=questionService.getQuestionById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
