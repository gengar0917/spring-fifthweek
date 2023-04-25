package com.sparta.hanghaejwt.dto;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class CommentRequestDto {
    private Long comment_id;
    private Long board_id;
    private String writer;
    private String text;
}
