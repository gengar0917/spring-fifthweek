package com.sparta.hanghaejwt.entity;
// 이 정보대로 데이터 베이스에 저장 된다.


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaejwt.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// @NoArgsConstructor : 기본 생성자를 생성
// @NoArgsConstructor(force = true) 사용시 기본 값으로 초기화
@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    // JPA 로 테이블과 엔티티 매핑할 때 식별자로 사용할 필드 위에 @Id를 붙여서
    // PK(Primary Key)를 나타낸다.
    // GeneratedValue 에서 IDENTITY : 기본키 생성을 데이터 베이스에 위임
    // SEQUENCE : 데이터 베이스 Sequence Object 를 사용  DB Sequence 는 유일한 값을 순서대로 생성하는 특별한 데이터베이스오브젝트
    // DB 가 자동으로 숫자를 generate
    // AUTO : 기본 설정 값, 방언에 따라 세가지 다른 전략을 자동으로 지정
    // 기본 키 제약 조건 : NULL 이 아니다. 유일하다. 변하면 안 된다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    // 가입 정보와 게시판 정보가 함께 저장 되어야 하기 때문에
    // join 사용
    // uer_id 통해 Board 와 User 가 연관 됨을 명시
    // 즉시로딩 EAGER, 지연로딩 LAZY
    // ManyToOne : 외래키 이곳에 존재, Board 가 연관관계 주인
    // user_id : 필드명_참조하는테이블의컬럼명
    // ManyToOne, OneToMany : 즉시로딩, OneToMany, ManyToMany : 지연로딩 추천
    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;



    // Board 의 파라미터가 있는 생성자 (리턴 타입이 없다)
    // User 의 username, password 를 받아오기 위해 사용
    public Board(BoardRequestDto requestDto, User user){
        this.contents = requestDto.getContent();
        this.title = requestDto.getTitle();
        this.user = user;
    }

    // Board 의 메소드
    // 게시글 업데이트시 내용과 제목 변경한 것이 저장 될 수 있도록
    public void update(BoardRequestDto requestDto) {
        this.contents = requestDto.getContent();
        this.title = requestDto.getTitle();
    }
}


//    @JsonIgnore  // 컨트롤러에서 리턴할 때 이 부분만 빼고 보내준다.
//    // 또한 데이터 받아올 때에도 빼고 받아온다. -> 그래서 Dto 에도 password 필드 넣을 것