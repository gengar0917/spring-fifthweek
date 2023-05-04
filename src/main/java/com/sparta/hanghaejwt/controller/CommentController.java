package com.sparta.hanghaejwt.controller;

import com.sparta.hanghaejwt.dto.CommentRequestDto;
import com.sparta.hanghaejwt.dto.CommentResponseDto;
import com.sparta.hanghaejwt.dto.MessageStatusResponseDto;
import com.sparta.hanghaejwt.security.UserDetailsImpl;
import com.sparta.hanghaejwt.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/create/{board_id}")
    public CommentResponseDto createComment(@PathVariable Long board_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(board_id, commentRequestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/update/{comment_id}")
    public CommentResponseDto updateComment(@PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(comment_id, commentRequestDto, userDetails.getUser());
    }

//     댓글 삭제
    @DeleteMapping("/delete/{comment_id}")
    public MessageStatusResponseDto createComment(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(comment_id, userDetails.getUser());
    }

    // 좋아요 누르기
    @PutMapping("/like/{comment-id}")
    public MessageStatusResponseDto likeComment(@PathVariable(name = "comment-id") Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.likeComment(comment_id, userDetails.getUser());
    }
}
