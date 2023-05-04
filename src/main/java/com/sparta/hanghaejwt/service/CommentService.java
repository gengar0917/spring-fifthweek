package com.sparta.hanghaejwt.service;

import com.sparta.hanghaejwt.dto.CommentRequestDto;
import com.sparta.hanghaejwt.dto.CommentResponseDto;
import com.sparta.hanghaejwt.dto.MessageStatusResponseDto;
import com.sparta.hanghaejwt.entity.*;
import com.sparta.hanghaejwt.repository.BoardRepository;
import com.sparta.hanghaejwt.repository.CommentLikeRepository;
import com.sparta.hanghaejwt.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentLikeRepository commentLikeRepository;

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

        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(comment.getUser().getUsername())) {
                commentRepository.delete(comment);
            } else {
            return new MessageStatusResponseDto("해당 댓글의 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);
            }

        return new MessageStatusResponseDto("성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

    @Transactional
    public MessageStatusResponseDto likeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        int isLike = 0;

        Optional<CommentLike> like = commentLikeRepository.findByCommentAndUser(comment, user);

        //like.ispresent 존재하면 true 없으면 false
        if(like.isPresent()){ //존재하냐 안 하냐
            CommentLike commentLike = like.get();
            commentLikeRepository.delete(commentLike);
            isLike = -1;
        }else{
            CommentLike commentLike = new CommentLike(comment, user);
            commentLikeRepository.save(commentLike);
            isLike = 1;
        }

        comment.updateLike(isLike);

        return new MessageStatusResponseDto("좋아요를 눌렀습니다.", HttpStatus.OK);

        //postLike를 db에서 찾고 있으면 생성, 없으면 삭제
        //결과에 따라 포스트의 필드 수정 isLike에 1 혹은 -1 저장 후 updateLike에 인자로 반환해야 함
    }
}
