package com.sparta.hanghaejwt.dto;

import com.sparta.hanghaejwt.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto implements BoardAndComment{
    private Long id;
    private String title;
    private String content;
    //    @CreatedDate
    private LocalDateTime createdAt;
    //    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private String username;

    public BoardResponseDto(Board board) {
        this.username = board.getUser().getUsername();
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
