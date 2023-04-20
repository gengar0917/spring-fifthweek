package com.sparta.hanghaejwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class deleteDto {
    boolean success = true;

    public deleteDto(boolean success) {
        this.success = success;
    }
}

