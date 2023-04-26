package com.sparta.hanghaejwt.entity;

import com.sparta.hanghaejwt.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String text;

    public Comment(Board board, User user, CommentRequestDto requestDto) {
        this.board = board;
        this.user = user;
        this.text = requestDto.getText();
    }

    public void update(CommentRequestDto commentRequestDto){
        this.text = commentRequestDto.getText();
    }
}
