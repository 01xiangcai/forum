package com.yao.forum.mapper;

import com.yao.forum.model.Comment;
import com.yao.forum.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}