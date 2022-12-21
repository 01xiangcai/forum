package com.yao.forum.service;

import com.yao.forum.dto.PaginationDTO;
import com.yao.forum.dto.QuestionDTO;
import com.yao.forum.dto.QuestionQueryDTO;
import com.yao.forum.exception.CustomizeErrorCode;
import com.yao.forum.exception.CustomizeException;
import com.yao.forum.mapper.QuestionExtMapper;
import com.yao.forum.mapper.QuestionMapper;
import com.yao.forum.mapper.UserMapper;
import com.yao.forum.model.Question;
import com.yao.forum.model.QuestionExample;
import com.yao.forum.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class QuestionService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(String search, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        Integer totalPage;

        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        if (totalCount==0){
            return null;
        }


        //总页数展示，例如，总数据totalCount为12，size为5，12取模（%）5为2，不为0，需要三页来展示，为12/5再加上1，若是10%5则为0，两页。
        if (totalCount % size != 0) {
            totalPage = totalCount / size + 1;
        } else {
            totalPage = totalCount / size;
        }

        //判断传进来页码是否超过实际页码,页码越界的问题
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        //size*(page-1) ，offset从第几条数据开始，size为5，第一页数据展示为0-4，第二页为5-9，第三页为10-14。
        Integer offset = size < 0 ? 0 : size * (page - 1);
//        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        questionExample.setOrderByClause("gmt_create desc");
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
//        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //根据id找到用户（问题的发布者）
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将查出来的数据question类型转换为questionDTO
            BeanUtils.copyProperties(question, questionDTO);
            //将用户放到questionDTO中
            questionDTO.setUser(user);
            //放到结果集
            questionDTOList.add(questionDTO);
        }
        //循环结果集放到最终的paginationDTO的List<QuestionDTO> questions属性中。
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);

        Integer totalCount = (int)questionMapper.countByExample(questionExample);


        //总页数展示，例如，总数据totalCount为12，size为5，12取模（%）5为2，不为0，需要三页来展示，为12/5再加上1，若是10%5则为0，两页。
        if (totalCount % size != 0) {
            totalPage = totalCount / size + 1;
        } else {
            totalPage = totalCount / size;
        }

        //判断传进来页码是否超过实际页码,页码越界的问题
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        //size*(page-1) ，offset从第几条数据开始，size为5，第一页数据展示为0-4，第二页为5-9，第三页为10-14。
        Integer offset = size < 0 ? 0 : size * (page - 1);
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria().andCreatorEqualTo(userId);
        questionExample1.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample1,new RowBounds(offset,size));
//        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample1,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //根据id找到用户（问题的发布者）
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将查出来的数据question类型转换为questionDTO
            BeanUtils.copyProperties(question, questionDTO);
            //将用户放到questionDTO中
            questionDTO.setUser(user);
            //放到结果集
            questionDTOList.add(questionDTO);
        }
        //循环结果集放到最终的paginationDTO的List<QuestionDTO> questions属性中。
        paginationDTO.setData(questionDTOList);
        return paginationDTO;

    }


    public QuestionDTO getQuestionById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        //将查出来的数据question类型转换为questionDTO
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }


    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        //标签判空
        if (StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }

        String[] tags = StringUtils.split(queryDTO.getTag(), ',');
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionsDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionsDTOS;
    }


}
