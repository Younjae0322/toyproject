package com.example.toyproject.controller;

import com.example.toyproject.dto.UserDto;
import com.example.toyproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping("/api/user/join")
    public ResponseEntity<String> join(@RequestBody UserDto userDto) {
        // UserService를 통해 데이터베이스에 저장
        userService.saveUser(userDto);

        // 정상적으로 저장된 경우 응답 코드 201 반환
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 완료되었습니다.");
    }

}
