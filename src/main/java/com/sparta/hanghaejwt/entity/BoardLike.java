package com.sparta.hanghaejwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardLike_id;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public BoardLike(Board board, User user) {
        this.board = board;
        this.user = user;
    }

//    public updateLike(Long board_id){
//
//    }
}
