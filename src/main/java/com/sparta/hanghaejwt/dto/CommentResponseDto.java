package com.sparta.hanghaejwt.dto;

import com.sparta.hanghaejwt.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto implements BoardAndComment{
    private Long comment_id;
    private String text;
    private String writer;

//    private Long board_id;

    public CommentResponseDto(Comment comment){
        this.comment_id = comment.getComment_id();
        this.text = comment.getText();
        this.writer = comment.getUser().getUsername();
    }
}


// comment id, content , createdAt, modifiedAt, username