package com.sparta.hanghaejwt.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaejwt.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 이 정보대로 데이터 베이스에 저장 된다.
@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 가입 정보와 게시판 정보가 함께 저장 되어야 하기 때문에
    // join 사용
    @JsonIgnore
    @JoinColumn(name = "username")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

//    @JsonIgnore  // 컨트롤러에서 리턴할 때 이 부분만 빼고 보내준다.
//    // 또한 데이터 받아올 때에도 빼고 받아온다. -> 그래서 Dto 에도 password 필드 넣을 것
//    @Column(nullable = false)
//    private String password;

    //    @Column(nullable = false)
//    private String username;


    public Board(BoardRequestDto requestDto, User user){
        this.contents = requestDto.getContent();
        this.title = requestDto.getTitle();
        this.user = user;
//        this.password = requestDto.getPassword();
//        this.username = requestDto.getUsername();

    }


    public void update(BoardRequestDto requestDto) {
        this.contents = requestDto.getContent();
        this.title = requestDto.getTitle();
//        this.username = requestDto.getUsername();
    }


}
