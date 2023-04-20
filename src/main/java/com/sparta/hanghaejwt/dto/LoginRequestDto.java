package com.sparta.hanghaejwt.dto;

import lombok.Getter;
import lombok.Setter;

// 로그인 시 username, password 만 요구
@Setter
@Getter
public class LoginRequestDto {
    private String username;
    private String password;

}
