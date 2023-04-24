package com.sparta.hanghaejwt.controller;


import com.sparta.hanghaejwt.dto.BoardRequestDto;
import com.sparta.hanghaejwt.dto.BoardResponseDto;
import com.sparta.hanghaejwt.dto.UserResponseDto;
import com.sparta.hanghaejwt.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 게시물 저장
    //
    @PostMapping("/create")  // post 는 body 가 있음
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
//        BoardService boardService = new BoardService();  // 다른 클래스의 메소드를 부르기 위해서 인스턴스 생성 -> 각 메소드에 넣으면 여러번 해야하기 때문에 전역에 놓으면 된다.
        return boardService.createBoard(requestDto, request);  // 만든 인스턴스를 이용하여 메소드 부르기
    }

    // 게시물 전체 보기
    @GetMapping("/list")  // get 은 body 가 없다
    public List<BoardResponseDto> getBoardList() { // 데이터 베이스에 저장 된 전체 게시물 전부다 가져오는 API
        return boardService.getBoardList();
    }

    // 게시물 하나만 보기
    @GetMapping("/{id}")  // http://localhost:8080/board/{id}
    public BoardResponseDto getBoard(@PathVariable Long id) { // 똑같은 id 를 갖고 있는 것을 가져올 것
        // 응답 보내기 : request Header 안에 들어 있는 Token 값 넣어 주기
        return boardService.getBoard(id);
    }

    // 게시물 수정  = post + get(게시물하나보기)
    @PutMapping("/update/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.updateBoard(id, requestDto, request);
    }

    //게시물 삭제
    @DeleteMapping("/delete/{id}")
    public UserResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }
}