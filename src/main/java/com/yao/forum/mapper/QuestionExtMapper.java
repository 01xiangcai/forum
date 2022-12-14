package com.yao.forum.mapper;

import com.yao.forum.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}
