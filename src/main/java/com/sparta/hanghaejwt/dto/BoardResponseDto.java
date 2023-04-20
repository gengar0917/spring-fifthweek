package com.sparta.hanghaejwt.dto;

import com.sparta.hanghaejwt.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String username;
    private String content;
    //    @CreatedDate
    private LocalDateTime createdAt;
    //    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    // board 필드를 dto 로 변경
    public static BoardResponseDto from(Board board) {
        return new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getUsername(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt());
    }

}
