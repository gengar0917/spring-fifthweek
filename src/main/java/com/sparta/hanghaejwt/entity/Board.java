package com.sparta.hanghaejwt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.hanghaejwt.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @Column (name = "board_like")
    private int boardLike = 0;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Comment> comments;

    public Board(BoardRequestDto requestDto, User user){
        this.contents = requestDto.getContent();
        this.title = requestDto.getTitle();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto) {
        this.contents = requestDto.getContent();
        this.title = requestDto.getTitle();
    }

    public void updateLike(int isLike){
        this.boardLike += isLike;
    }
}