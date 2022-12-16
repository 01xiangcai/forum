package com.yao.forum.Controller;

import com.yao.forum.dto.CommentDTO;
import com.yao.forum.dto.QuestionDTO;
import com.yao.forum.enums.CommentTypeEnum;
import com.yao.forum.exception.CustomizeErrorCode;
import com.yao.forum.exception.CustomizeException;
import com.yao.forum.model.User;
import com.yao.forum.service.CommentService;
import com.yao.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String quetions(@PathVariable(name = "id") Long id,
                           Model model,
                           HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            throw  new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        QuestionDTO questionDTO=questionService.getQuestionById(id);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //增加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOS);
        return "question";
    }
}
