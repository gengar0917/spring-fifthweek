package com.sparta.hanghaejwt.controller;

import com.sparta.hanghaejwt.dto.CommentRequestDto;
import com.sparta.hanghaejwt.dto.CommentResponseDto;
import com.sparta.hanghaejwt.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    // 댓글 만들기
    @PostMapping("/create/{board_id}")
    public CommentResponseDto createComment(@PathVariable Long board_id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.createComment(board_id, commentRequestDto, request);
    }


    // 댓글 수정 : 게시글 id - Long id / 댓글 id
    @PutMapping("/update/{board_id}")
    public CommentResponseDto updateComment(@PathVariable Long board_id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(board_id, commentRequestDto, request);
    }

//     댓글 삭제
    @DeleteMapping("/delete/{comment_id}")
    public MessageStatusResponseDto createComment(@PathVariable Long comment_id, HttpServletRequest request){
        return commentService.deleteComment(comment_id, request);
    }
}
