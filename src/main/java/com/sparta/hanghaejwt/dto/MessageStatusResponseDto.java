package com.sparta.hanghaejwt.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// 회원가입, 로그인 시 성공할 경우 메세지와 상태 코드 반환해주는 Dto
// 제네릭을 이용하여 공통 응답 API 만들어 보기 :)

@Getter
//@RequiredArgsConstructor  // final 만 생성
@AllArgsConstructor  // 파라미터가 있는 생성자 자동 생성
public class MessageStatusResponseDto {

    private String msg;

    // static 정적 메소드
    // 회원 가입 성공시 상태코드와 메세지 전달
    public static ResponseEntity<String> setStatus(String msg, HttpStatus status) {
        return new ResponseEntity<>(msg, status);
    }

    public MessageStatusResponseDto(String msg, HttpStatus status){
        this.msg = msg;
        this.status = String.valueOf(status);
    }
//
//    // static 정적 메소드
//    // 로그인 성공시 상태코드와 메세지 전달
//    public static MessageStatusResponseDto setLogin() {
//        return new MessageStatusResponseDto(200, "로그인 성공");
//    }
//
//    // 게시물 삭제 성공시 상태 코드와 메세지 전달
//    public static ResponseEntity<String> deleteDto(String msg, HttpStatus status){
//        return new ResponseEntity<>(200, "삭제 완료");
//    }
//
//    public static ResponseEntity<String> deleteComment(String msg, HttpStatus status) {
//        return new ResponseEntity<>(msg, status);
//    }
}



// ResponseEntity<>(body : "회원가입 성공", HttpStatus.CREATED) : 상태 코드와 메세지

