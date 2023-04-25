package com.sparta.hanghaejwt.dto;

import com.sparta.hanghaejwt.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long comment_id;
    private Long board_id;
    private String writer;
    private String text;

    public CommentResponseDto(Comment comment){
        this.comment_id = comment.getComment_id();
        this.board_id = comment.getBoard_id();
        this.writer = comment.getWriter();
        this.text = comment.getText();
    }
}
