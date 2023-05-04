package com.sparta.hanghaejwt.dto;

import com.sparta.hanghaejwt.entity.Board;
import com.sparta.hanghaejwt.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto{
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;
    private int boardLike;
    private List<Comment> comments;

    public BoardResponseDto(Board board) {
        this.username = board.getUser().getUsername();
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.boardLike = board.getBoardLike();
        this.comments = board.getComments();
    }
}
