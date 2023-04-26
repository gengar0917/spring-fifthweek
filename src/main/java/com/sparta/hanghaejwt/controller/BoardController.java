package com.sparta.hanghaejwt.controller;

// 전역 예외 처리
import com.sparta.hanghaejwt.dto.*;
import com.sparta.hanghaejwt.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    // @RequiredArgsConstructor 어노테이션을 사용하여 BoardService 의 인스턴스 boardService 를 생성
    // @RequiredArgsConstructor : final 이 붇거나 @NotNull 이 붙은 필드 생성자를 자동 생성해주는 롬복 어노테이션
    // 모든 메소드에서 사용할 수 있도록 전역으로 두었다.
    private final BoardService boardService;

    // 게시물 저장
    // @RequestBody : 프론트에서 넘어오는 json 데이터 받음
    // HttpServletRequest : Http 프로토콜의 request 정보를 서블릿에 전달하기 위한 목적으로 사용하며, Header 정보를 읽는다.
    // boardService 의 createdBoard 메소드 호출
    // 게시글을 만들기 위해서 게시글의 제목과 내용이 필요하여 BoardRequestDto 를 파라미터 1로,
    // 게시글에 유저 정보를 받아오기 위해서 HttpServletRequest 를 파라미터 2로 선언하였다.
    @PostMapping("/create")  // post 는 body 가 있음
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
//        BoardService boardService = new BoardService();  // 다른 클래스의 메소드를 부르기 위해서 인스턴스 생성 -> 각 메소드에 넣으면 여러번 해야하기 때문에 전역에 놓으면 된다.
        return boardService.createBoard(requestDto, request);  // 만든 인스턴스를 이용하여 메소드 부르기
    }

    // 게시물 전체 보기
    // boardService 의 getBoardList 호출
    // get 은 body 가 없기 때문에 RequestBody 사용하지 않는다.
    // list 를 불러오는 것이기 때문에 필요한 데이터가 없어서 파라미터가 필요없다.
    @GetMapping("/list")  // get 은 body 가 없다
    public List<BoardAndComment> getBoardList() { // 데이터 베이스에 저장 된 전체 게시물 전부다 가져오는 API
        return boardService.getBoardList();
    }

    // 게시물 하나만 보기
    // @PathVariable : URI 에서 가장 끝부분에 변수 같은 부분 처리해 준다.
    // id 를 파라미터로 받아서 비교하기
    @GetMapping("/{id}")  // http://localhost:8080/board/{id}
    public BoardCommentResponseDto getBoard(@PathVariable Long id) { // 똑같은 id 를 갖고 있는 것을 가져올 것
        // 응답 보내기 : request Header 안에 들어 있는 Token 값 넣어 주기
        return boardService.getBoard(id);
    }

    // 게시물 수정  = post + get(게시물하나보기)
    // @PathVariable 에서 id 를 받고, @RequestBody 를 이용하여 게시글 수정할 내용 받고,
    // HttpServletRequest 를 이용하여 http header 에 있는 사용자 정보를 받는다.
    // boardService 에 있는 updateBoard 메소드의 리턴 타입은 BoardResponseDto 이다.
    @PutMapping("/update/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.updateBoard(id, requestDto, request);
    }

    // 게시물 삭제
    // 삭제할 게시물의 id 와 유저 정보를 알아 내기 위하여 id 와 request 를 받는다.
    @DeleteMapping("/delete/{id}")
    public MessageStatusResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }
}


/*
Request 가 들어오는 타입에 따라 받는 방법은 4가지 정도로 달라진다.
@PathVariable
맨 뒤에 처리해주는 것
 - URL 변수
@RequestParam
 - Query String
@RequestBody
 - Body
@ModelAttribute
 - Form
 */