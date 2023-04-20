package com.sparta.hanghaejwt.dto;

import lombok.*;
// 회원가입, 로그인 시 성공할 경우 메세지와 상태 코드 반환해주는 Dto
// 제네릭을 이용하여 공통 응답 API 만들어 보기 :)

@Getter
//@RequiredArgsConstructor  // final 만 생성
@AllArgsConstructor  // 파라미터가 있는 생성자 자동 생성
public class UserResponseDto {
    // 상태 코드와 메세지
    private final int statusCode;
    private String msg;

    // static 정적 메소드
    // 회원 가입 성공시 상태코드와 메세지 전달
    public static UserResponseDto setSuccess() {
        return new UserResponseDto(200, "회원 가입 성공");
    }

    // static 정적 메소드
    // 로그인 성공시 상태코드와 메세지 전달
    public static UserResponseDto setLogin() {
        return new UserResponseDto(200, "로그인 성공");
    }
}
