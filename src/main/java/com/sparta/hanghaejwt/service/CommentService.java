package com.sparta.hanghaejwt.service;

import com.sparta.hanghaejwt.dto.CommentRequestDto;
import com.sparta.hanghaejwt.dto.CommentResponseDto;
import com.sparta.hanghaejwt.dto.MessageStatusResponseDto;
import com.sparta.hanghaejwt.entity.Board;
import com.sparta.hanghaejwt.entity.Comment;
import com.sparta.hanghaejwt.entity.User;
import com.sparta.hanghaejwt.entity.UserRoleEnum;
import com.sparta.hanghaejwt.repository.BoardRepository;
import com.sparta.hanghaejwt.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    //댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long board_id, CommentRequestDto commentRequestDto, User user) {

        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );

        Comment comment = new Comment(board, user, commentRequestDto);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    //댓글 수정 :)
    @Transactional
    public CommentResponseDto updateComment(Long comment_id, CommentRequestDto commentRequestDto, User user) {

        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        if (user.getRole() == UserRoleEnum.USER || user.getUsername().equals(comment.getUser().getUsername())) {
                comment.update(commentRequestDto);
            } else {
                MessageStatusResponseDto.setStatus("해당 댓글의 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);
            }

        return new CommentResponseDto(comment);
    }


    //댓글 삭제
    @Transactional
    public MessageStatusResponseDto deleteComment(Long comment_id, User user) {

        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        if (user.getRole() == UserRoleEnum.USER || user.getUsername().equals(comment.getUser().getUsername())) {
                commentRepository.delete(comment);
            } else {
            return new MessageStatusResponseDto("해당 댓글의 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);
            }

        return new MessageStatusResponseDto("성공적으로 삭제되었습니다.", HttpStatus.OK);
    }
}
