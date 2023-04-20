package com.sparta.hanghaejwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanghaeJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeJwtApplication.class, args);
    }

}
