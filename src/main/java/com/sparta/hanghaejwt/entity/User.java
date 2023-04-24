package com.sparta.hanghaejwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id//PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 식별자 값 유저를 만들때마다 ++해주니까 null && 중복 없음
    @Column(name = "user_id")
    private Long id;

    // nullable = true : null 허용
    // unique = false : 중복 허용
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
