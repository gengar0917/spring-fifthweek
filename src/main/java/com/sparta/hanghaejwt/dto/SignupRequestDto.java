package com.sparta.hanghaejwt.dto;

import com.sparta.hanghaejwt.entity.UserRoleEnum;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// 회원 가입 : username password 만 필요
@Setter
@Getter
//@AllArgsConstructor
public class SignupRequestDto {
    @NotNull
    @Size(min = 4, max = 10)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}",
    message = "아이디 4~10자 영문 소문자, 숫자를 사용하세요.")
    private String username;
//    /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/g;

    @NotNull
    @Size(min = 8, max = 15)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[,.!@/?#$%^&*+-])(?=.*[a-zA-Z]).{8,15}",
    message = "비밀번호는 8~16자 영문 대 소문자, 특수문자, 그리고 숫자를 사용하세요.")
    private String password;

    private String email;

    private boolean admin = false;
    private String adminToken = "";
//    private String adminToken ="";

}
