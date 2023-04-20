package com.sparta.hanghaejwt.service;

import com.sparta.hanghaejwt.dto.*;
import com.sparta.hanghaejwt.entity.Board;
import com.sparta.hanghaejwt.entity.User;
import com.sparta.hanghaejwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// user 회원가입, 로그인을 위한 클래스
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 가입
    @Transactional
    public UserResponseDto signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()) {  // username 이 이미 있는 경우
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);

        return UserResponseDto.setSuccess();
    }

    // 로그인
    @Transactional
    public UserResponseDto login(LoginRequestDto loginRequestDto){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 일치 확인
        if (!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 아이디 일치 확인
        if (!user.getUsername().equals(username)) {
            throw new IllegalArgumentException("아이디가 일치하지 않습니다.");
        }
        return UserResponseDto.setLogin();
    }
}
