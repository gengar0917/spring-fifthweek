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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;
    private final FindService findService;

    // 게시물 저장
    // 토큰 있는지 확인 후 저장
    // Controller 에서 들어온 requestDto(게시글요청 Dto) 와 request(http header 내용) 을 이용

    // Claim : JSON Map 으로 어떤 값이든 붙일 수 있으나 JWT 표준이름은 getter 와 setter 제공
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        // 토큰이 없는 경우
        if (token == null) {
            MessageStatusResponseDto.setStatus("토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        User user = findService.findUser(request);
//
//        // 토큰이 있는 경우만
//        // 유효한 토큰인지 확인
//        if (jwtUtil.validateToken(token)) {
//            // 토큰에서 사용자 정보 가져오기
//            claims = jwtUtil.getUserInfoFromToken(token);
//        } else {
//            throw new IllegalArgumentException("Token Error");
//        }
//
//        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회하여 사용자 존재 유무 확인
//        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//        );
        Board board = new Board(requestDto, user);
//
        return new BoardResponseDto(boardRepository.save(board));
    }

    // 게시물 전체 보기 : stream
    // 댓글 같이 보기
    // 토큰 필요 없음
    public List<BoardAndComment> getBoardList() {  // 데이터 베이스에 저장 된 전체 게시물 전부다 가져오는 API
        // 테이블에 저장 되어 있는 모든 게시물 목록 조회
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc();

        // DB 에서 가져온 것
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
    // 댓글 같이 보기
    // 토큰 필요 없음
    public BoardCommentResponseDto getBoard(Long id) { // 똑같은 id 를 갖고 있는 것을 가져올 것
        Board board = findService.findBoard(id);
        // 댓글 뽑아 오기
        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc();
        //
        List<CommentResponseDto> comments = new ArrayList();

        for(Comment comment : commentList){
            if(comment.getBoard().getId() == id){
                comments.add(new CommentResponseDto(comment));
            }
        }

        return new BoardCommentResponseDto(new BoardResponseDto(board), comments);
    }

    // 게시물 수정  = post + get(게시물하나보기)
    // 게시물 일치 확인 + 토큰 유효 확인
    // 둘다 해당되면 사용자가 작성한 게시글만 수정 가능
    // Transactional : 매서드 시작하면 트랜잭션 시작, 성공적으로 메소드가 끝나면 트랜잭션 커밋
    // 중간에 문제가 발생하면 롤백
    // 하나의 작업을 수행하는 서비스 계층 메서드에 붙이는 것이 통상적이다.
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        // 게시물의 id 가 같은지 확인
        Board board = findService.findBoard(id);
        // 토큰 유효 검사 + 게시물 존재 확인
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        // 토큰이 없는 경우
        if (token == null) {
            MessageStatusResponseDto.setStatus("토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

//        // 토큰이 있는 경우만
//        // 유효한 토큰인지 확인
//        if (jwtUtil.validateToken(token)) {
//            // 토큰에서 사용자 정보 가져오기
//            claims = jwtUtil.getUserInfoFromToken(token);
//        } else {
//            throw new IllegalArgumentException("Token Error");
//        }
//
//        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회하여 사용자 존재 유무 확인
//        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//        );

        User user = findService.findUser(request);

        // 사용자 권한 확인하여 ADMIN 이면 아무 게시글이나 삭제 가능, USER 면 본인 글만 삭제 가능
        UserRoleEnum userRoleEnum = user.getRole();

        if (userRoleEnum == UserRoleEnum.ADMIN){
            board.update(requestDto);
            boardRepository.save(board);
        } else {
            if (user.getUsername().equals(board.getUser().getUsername())){
                board.update(requestDto);
                boardRepository.save(board);
            } else {
                MessageStatusResponseDto.setStatus("해당 게시글의 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);
            }
        }
        return new BoardResponseDto(board);
    }

    //게시물 삭제
    public MessageStatusResponseDto deleteBoard(Long id, HttpServletRequest request) {
        // 게시물 id 선택 및 존재 여부 확인
        Board board = findService.findBoard(id);

        // 토큰 유효 검사 + 게시물 존재 확인
        User user = findService.findUser(request);

        // 사용자 권한 확인하여 ADMIN 이면 아무 게시글이나 삭제 가능, USER 면 본인 글만 삭제 가능
        UserRoleEnum userRoleEnum = user.getRole();
        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc();

        // DB 에서 가져온 것
        List<BoardAndComment> lists = new ArrayList();

        if (userRoleEnum == UserRoleEnum.ADMIN){
            for(Comment comment : commentList){
                if(comment.getBoard().getId() == board.getId()){
                    commentRepository.delete(comment);
                }
            }
            boardRepository.delete(board);
        } else {
            if (user.getUsername().equals(board.getUser().getUsername())){
                for(Comment comment : commentList){
                    if(comment.getBoard().getId() == board.getId()){
                        commentRepository.delete(comment);
                    }
                }
                boardRepository.delete(board);
            } else {
                MessageStatusResponseDto.setStatus("해당 게시글의 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);
            }
        }

        return new MessageStatusResponseDto("성공적으로 삭제하였습니다.", HttpStatus.OK);
    }
}


