package com.sparta.hanghaejwt.service;
// 예외처리를 좀 더 깔끔하게 할 수 있는 방법은 없는가?

import com.sparta.hanghaejwt.dto.*;
import com.sparta.hanghaejwt.entity.*;
import com.sparta.hanghaejwt.jwt.JwtUtil;
import com.sparta.hanghaejwt.repository.BoardLikeRepository;
import com.sparta.hanghaejwt.repository.BoardRepository;
import com.sparta.hanghaejwt.repository.CommentRepository;
import com.sparta.hanghaejwt.repository.UserRepository;
import com.sparta.hanghaejwt.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
//    private final CommentRepository commentRepository;
    private final BoardLikeRepository boardLikeRepository;

    // 게시물 저장
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto, user);
        return new BoardResponseDto(boardRepository.save(board));
    }

    // 게시물 전체 보기
    @Transactional
    public List<BoardResponseDto> getBoardList() {
//        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
//        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc();

//        return boardList.stream().map(BoardResponseDto::from).collect(Collectors.toList());

        List<BoardResponseDto> lists = new ArrayList();

//        for(Board board : boardList){
//            lists.add(new BoardResponseDto(board));
//            for(Comment comment : commentList){
//                if(comment.getBoard().getId() == board.getId()){
//                    lists.add(new CommentResponseDto(comment));
//                }
//            }
//        }

//        for(Board board : boardList){
//            lists.add(new BoardResponseDto(board));
//        }
//
//        return lists;
        List<BoardResponseDto> boardList = boardRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return boardList;
    }

    // 게시물 하나만 보기
    @Transactional
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

//        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc();
//
////        List<CommentResponseDto> comments = new ArrayList();
////
////        for(Comment comment : commentList){
////            if(comment.getBoard().getId() == id){
////                comments.add(new CommentResponseDto(comment));
////            }
////        }

//        return new BoardCommentResponseDto(new BoardResponseDto(board), comments);
        return new BoardResponseDto(board);
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

    //게시물 좋아요 누르기
    @Transactional
    public MessageStatusResponseDto likeBoard(Long id, User user) {
        // 게시물 id 선택 및 존재 여부 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        int isLike = 0;
//        User boardLikeUser = boardLikeRepository.findByBoardAndUser(board, user).getUser();
//
//        if(boardLikeUser == user){
//            boardLikeRepository.delete(new BoardLike(board, user));
//            isLike = -1;
//        }else{
//            boardLikeRepository.save(new BoardLike(board, user));
//            isLike = 1;
//        }
        Optional<BoardLike> like = boardLikeRepository.findByBoardAndUser(board, user);

        //like.ispresent 존재하면 true 없으면 false
        if(like.isPresent()){ //존재하냐 안 하냐
            BoardLike boardLike = like.get();
            boardLikeRepository.delete(boardLike);
            isLike = -1;
        }else{
            BoardLike boardLike = new BoardLike(board, user);
            boardLikeRepository.save(boardLike);
            isLike = 1;
        }

        board.updateLike(isLike);

        return new MessageStatusResponseDto("좋아요를 눌렀습니다.", HttpStatus.OK);

        //postLike를 db에서 찾고 있으면 생성, 없으면 삭제
        //결과에 따라 포스트의 필드 수정 isLike에 1 혹은 -1 저장 후 updateLike에 인자로 반환해야 함
    }

}


