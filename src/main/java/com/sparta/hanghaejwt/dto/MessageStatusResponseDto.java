package com.sparta.hanghaejwt.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class MessageStatusResponseDto {

    private String msg;
    private String status;

    public static ResponseEntity<String> setStatus(String msg, HttpStatus status) {
        return new ResponseEntity<>(msg, status);
    }

    public MessageStatusResponseDto(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = String.valueOf(status);
    }
}

