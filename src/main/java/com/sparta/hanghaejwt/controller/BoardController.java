package com.sparta.hanghaejwt.controller;

// 전역 예외 처리
import com.sparta.hanghaejwt.dto.*;
import com.sparta.hanghaejwt.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.sparta.hanghaejwt.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    // 게시물 저장
    @PostMapping("/create")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    // 게시물 전체 보기
    @GetMapping("/list")
    public List<BoardResponseDto> getBoardList() { // 데이터 베이스에 저장 된 전체 게시물 전부다 가져오는 API
        return boardService.getBoardList();
    }

    // 게시물 하나만 보기
    @GetMapping("/{board-id}")
    public BoardResponseDto getBoard(@PathVariable(name = "board-id") Long id) {
        return boardService.getBoard(id);
    }

    // 게시물 수정
    @PutMapping("/update/{board-id}")
    public BoardResponseDto updateBoard(@PathVariable(name = "board-id") Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    // 게시물 삭제
    @DeleteMapping("/delete/{board-id}")
    public MessageStatusResponseDto deleteBoard(@PathVariable(name = "board-id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }

    //게시물 좋아요 누르기
    @PutMapping("/like/{board-id}")
    public MessageStatusResponseDto likeBoard(@PathVariable(name = "board-id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.likeBoard(id, userDetails.getUser());
    }
}