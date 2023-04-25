package com.sparta.hanghaejwt.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long comment_id;

    @Column(nullable = false)
    private Long board_id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String text;

}
