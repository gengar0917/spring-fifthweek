package com.sparta.hanghaejwt.service;

import com.sparta.hanghaejwt.dto.CommentRequestDto;
import com.sparta.hanghaejwt.dto.CommentResponseDto;
import com.sparta.hanghaejwt.entity.Comment;
import com.sparta.hanghaejwt.entity.User;
import com.sparta.hanghaejwt.jwt.JwtUtil;
import com.sparta.hanghaejwt.repository.CommentRepository;
import com.sparta.hanghaejwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.Jar;
import org.json.HTTP;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final Comment comment;
    private final JwtUtil jwtUtil;

    //댓글 생성
    public CommentResponseDto createComment(Long board_id, CommentRequestDto commentRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 없는 경우
        if (token == null){
            throw new IllegalArgumentException("토큰 없음");
        }

        // 토큰이 있는 경우만
        // 유효한 토큰인지 확인
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 가져온 사용자 정보를 이용하여 DB 조회하여 사용자 존재 유무 확인
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        return new CommentResponseDto(commentRepository.save(comment));
    }

    //댓글 수정 :)
    public CommentResponseDto updateComment(Long board_id, CommentRequestDto commentRequestDto, HttpServletRequest request){

    }

    //댓글 삭제
    public CommentResponseDto deleteComment(Long id, HttpServletRequest request){

    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 댓글이 없습니다.")
        );
    }

}
