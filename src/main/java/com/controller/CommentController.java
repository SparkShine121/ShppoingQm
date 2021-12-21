package com.controller;

import com.entity.Comment;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/getCommentList")
    public ModelAndView list() {
        //可以考虑把父评论往下的评论信息的pid都指向同一个，一个前端只能做二级循环
        List<Comment> lists = commentService.findCommentAll(0);
        ModelAndView mav = new ModelAndView("/comment");
        mav.addObject("lists", lists);
        return mav;
    }
}
