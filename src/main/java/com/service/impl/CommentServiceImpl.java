package com.service.impl;

import com.entity.Comment;
import com.mapper.CommentMapper;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 递归查询评论信息
     */
    @Override
    public List<Comment> findCommentAll(int parentId) {
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        diGui(commentArrayList, parentId);
        return commentArrayList;
    }

    private void diGui(List<Comment> commentList, int parentId) {
        //递归获取所有子类别，并组合成为一个“目录树”
        List<Comment> list = commentMapper.selectByParentId(parentId);
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                Comment comment = list.get(i);
                commentList.add(comment);
                diGui(comment.getReplyComments(), comment.getCommentId());
            }
        }
    }
}
