package com.yao.forum.service;

import com.yao.forum.dto.PaginationDTO;
import com.yao.forum.dto.QuestionDTO;
import com.yao.forum.mapper.QuestionMapper;
import com.yao.forum.mapper.UserMapper;
import com.yao.forum.model.Question;
import com.yao.forum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        //判断传进来页码是否超过实际页码,页码越界的问题
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        //size*(page-1) ，offset从第几条数据开始，size为5，第一页数据展示为0-4，第二页为5-9，第三页为10-14。
        Integer offset = size < 0 ? 0 : size * (page - 1);

        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //根据id找到用户（问题的发布者）
            User user = userMapper.finfById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将查出来的数据question类型转换为questionDTO
            BeanUtils.copyProperties(question, questionDTO);
            //将用户放到questionDTO中
            questionDTO.setUser(user);
            //放到结果集
            questionDTOList.add(questionDTO);
        }
        //循环结果集放到最终的paginationDTO的List<QuestionDTO> questions属性中。
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount, page, size);

        //判断传进来页码是否超过实际页码,页码越界的问题
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        //size*(page-1) ，offset从第几条数据开始，size为5，第一页数据展示为0-4，第二页为5-9，第三页为10-14。
        Integer offset = size < 0 ? 0 : size * (page - 1);

        List<Question> questions = questionMapper.listByUserId(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //根据id找到用户（问题的发布者）
            User user = userMapper.finfById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将查出来的数据question类型转换为questionDTO
            BeanUtils.copyProperties(question, questionDTO);
            //将用户放到questionDTO中
            questionDTO.setUser(user);
            //放到结果集
            questionDTOList.add(questionDTO);
        }
        //循环结果集放到最终的paginationDTO的List<QuestionDTO> questions属性中。
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;

    }


    public QuestionDTO getQuestionById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        //将查出来的数据question类型转换为questionDTO
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.finfById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            //更新
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
