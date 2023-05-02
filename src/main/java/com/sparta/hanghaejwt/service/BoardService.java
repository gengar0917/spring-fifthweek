package com.sparta.hanghaejwt.service;
// 예외처리를 좀 더 깔끔하게 할 수 있는 방법은 없는가?

import com.sparta.hanghaejwt.dto.*;
import com.sparta.hanghaejwt.entity.Board;
import com.sparta.hanghaejwt.entity.Comment;
import com.sparta.hanghaejwt.entity.User;
import com.sparta.hanghaejwt.entity.UserRoleEnum;
import com.sparta.hanghaejwt.jwt.JwtUtil;
import com.sparta.hanghaejwt.repository.BoardRepository;
import com.sparta.hanghaejwt.repository.CommentRepository;
import com.sparta.hanghaejwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 게시물 저장
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto, user);
        return new BoardResponseDto(boardRepository.save(board));
    }

    // 게시물 전체 보기
    @Transactional
    public List<BoardAndComment> getBoardList() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc();

//        return boardList.stream().map(BoardResponseDto::from).collect(Collectors.toList());
        List<BoardAndComment> lists = new ArrayList();

        for(Board board : boardList){
            lists.add(new BoardResponseDto(board));
            for(Comment comment : commentList){
                if(comment.getBoard().getId() == board.getId()){
                    lists.add(new CommentResponseDto(comment));
                }
            }
        }

        return lists;
    }

    // 게시물 하나만 보기
    @Transactional
    public BoardCommentResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc();

        List<CommentResponseDto> comments = new ArrayList();

        for(Comment comment : commentList){
            if(comment.getBoard().getId() == id){
                comments.add(new CommentResponseDto(comment));
            }
        }

        return new BoardCommentResponseDto(new BoardResponseDto(board), comments);
    }

    // 게시물 수정
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto, User user) {
        // 게시물의 id 가 같은지 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Long loginUserId = user.getId();
        if (!board.getUser().getId().equals(loginUserId)) {
            throw new IllegalArgumentException("회원님께서 작성한 보드가 아닙니다.");
        }

        UserRoleEnum userRoleEnum = user.getRole();

        if (userRoleEnum == UserRoleEnum.ADMIN || user.getUsername().equals(board.getUser().getUsername())) {
            board.update(requestDto);
            boardRepository.save(board);
        }else {
                MessageStatusResponseDto.setStatus("해당 게시글의 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);
            }
        return new BoardResponseDto(board);
        }



    //게시물 삭제
    @Transactional
    public MessageStatusResponseDto deleteBoard(Long id, User user) {
        // 게시물 id 선택 및 존재 여부 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        UserRoleEnum userRoleEnum = user.getRole();

        if(userRoleEnum == UserRoleEnum.ADMIN || user.getUsername().equals(board.getUser().getUsername())){
            boardRepository.delete(board);
        }else{
            return new MessageStatusResponseDto("해당 게시글의 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);
        }

        return new MessageStatusResponseDto("성공적으로 삭제하였습니다.", HttpStatus.OK);
    }
}


