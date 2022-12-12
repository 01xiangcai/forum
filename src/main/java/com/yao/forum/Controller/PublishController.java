package com.yao.forum.Controller;

import com.yao.forum.dto.QuestionDTO;
import com.yao.forum.mapper.QuestionMapper;
import com.yao.forum.mapper.UserMapper;
import com.yao.forum.model.Question;
import com.yao.forum.model.User;
import com.yao.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        QuestionDTO question = questionService.getQuestionById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String dopublish(HttpServletRequest request,
                            Model model,
                            @RequestParam("title")  String title,
                            @RequestParam("description")  String description,
                            @RequestParam("tag")  String tag,
                            @RequestParam("id") Integer id
                            ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user =(User) request.getSession().getAttribute("user");

        //用户为空
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        //用户不为空
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        question.setId(id);
        questionService.createOrUpate(question);
        return "redirect:/";
    }
}
