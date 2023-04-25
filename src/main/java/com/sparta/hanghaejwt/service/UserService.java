package com.sparta.hanghaejwt.service;

import com.sparta.hanghaejwt.dto.*;
import com.sparta.hanghaejwt.entity.User;
import com.sparta.hanghaejwt.entity.UserRoleEnum;
import com.sparta.hanghaejwt.jwt.JwtUtil;
import com.sparta.hanghaejwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

// user 회원가입, 로그인을 위한 클래스
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원 가입
    // username 과 password 를 controller 에서 받아온 파라미터에서 추출해낸다.
    // userRepository 에서 username 을 찾아와서
    @Transactional
    public UserResponseDto signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        // Optional : 값이 null 이어도 NPE(NullPointException) 발생하지 않는다. 빈 객체는 empty() 로 받을 수 있다.
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()) {  // username 이 이미 있는 경우
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 Role 확인

        UserRoleEnum role = UserRoleEnum.USER;

        String role2 = String.valueOf(signupRequestDto.getAdminToken());

        if(role2.equals(ADMIN_TOKEN)){
            role = UserRoleEnum.ADMIN;
        }else if(role2.equals("null")){
            role = UserRoleEnum.USER;
        }else if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
            throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
        }

        User user = new User(username, password, role); //Admin이어도 role 값으로 user 받음
        userRepository.save(user);

        return UserResponseDto.setSuccess();
    }

    // 로그인
    @Transactional
    public UserResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response){
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

        // addHeader : 헤더 쪽에 같이 넣어준다.
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return UserResponseDto.setLogin();
    }
}


/*
에러 처리 코드
() -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다")
 */
