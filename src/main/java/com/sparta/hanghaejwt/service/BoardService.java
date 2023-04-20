package com.sparta.hanghaejwt.service;


import com.sparta.hanghaejwt.dto.BoardRequestDto;
import com.sparta.hanghaejwt.dto.BoardResponseDto;
import com.sparta.hanghaejwt.dto.deleteDto;
import com.sparta.hanghaejwt.entity.Board;
import com.sparta.hanghaejwt.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시물 저장
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        // 브라우저에서 받아온 데이터 저장하기 위해서 Board 객체로 변환 - 그래서 리턴을 board 로 (BoardRepository 가 받아간다)
        Board board = new Board(requestDto);
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    // 게시물 전체 보기 : stream
    public List<BoardResponseDto> getBoardList() {  // 데이터 베이스에 저장 된 전체 게시물 전부다 가져오는 API
        // 테이블에 저장 되어 있는 모든 게시물 목록 조회
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        // DB 에서 가져온 것

        // list board to list boardResponseDto
//        List<BoardResponseDto> responseDtoList = boardList.stream().map(BoardResponseDto::from).collect(Collectors.toList());

        return boardList.stream().map(BoardResponseDto::from).collect(Collectors.toList());
    }

    // 게시물 하나만 보기
    public BoardResponseDto getBoard(Long id) { // 똑같은 id 를 갖고 있는 것을 가져올 것
        // 조회하기 위해 받아온 course 의 id 를 사용하여 해당 course 인스턴스가 테이블에 존재하는지 확인하고 가져오기
        Board board = checkBoard(id);

        return new BoardResponseDto(board);
    }

    private Board checkBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.")
        );
    }

    // 게시물 수정  = post + get(게시물하나보기)
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        // 수정하기 위해 받아온 board 의 id 를 사용하여 해당 board 인스턴스가 존재하는지 확인 후 가져오기
        Board board = checkBoard(id);
        if (!requestDto.getPassword().equals(board.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        board.update(requestDto);

        return new BoardResponseDto(board);
    }

    //게시물 삭제
    public deleteDto deleteBoard(Long id, BoardRequestDto requestDto) {
        // 삭제하기 위해 받아온 board 의 id 를 사용하여 해당 board 인스턴스가 존재하는지 확인 후 가져오기
        Board board = checkBoard(id);
        if (!requestDto.getPassword().equals(board.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        boardRepository.delete(board);

        return new deleteDto();
    }
}


