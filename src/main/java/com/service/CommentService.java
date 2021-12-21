package com.service;

import com.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findCommentAll(int parentId);
}
