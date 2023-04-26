package com.sparta.hanghaejwt.service;

import com.sparta.hanghaejwt.dto.MessageStatusResponseDto;
import com.sparta.hanghaejwt.entity.Board;
import com.sparta.hanghaejwt.entity.Comment;
import com.sparta.hanghaejwt.entity.User;
import com.sparta.hanghaejwt.jwt.JwtUtil;
import com.sparta.hanghaejwt.repository.BoardRepository;
import com.sparta.hanghaejwt.repository.CommentRepository;
import com.sparta.hanghaejwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class FindService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;

    //board_id를 이용해 해당 board 찾기
    public Board findBoard(Long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
       return board;
    }


    //HttpServletRequest 를 이용해 해당 user 찾기
    //토큰 검증
    public User findUser(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        // 토큰이 있는 경우만
        // 유효한 토큰인지 확인
        if (token != null) {
            if (jwtUtil.validateToken(token)) { // 토큰이 유효하면 사용자 확인
                Claims claims = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
                );

                return user;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않음");
//                throw new IllegalArgumentException("Token Error");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰 없음");
    }

    // 댓글 찾기
    public Comment findComment(Long comment_id){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("선택한 댓글이 없습니다")
        );
        return comment;
    }

}

