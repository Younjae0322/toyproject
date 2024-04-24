package com.example.toyproject.controller;

import com.example.toyproject.domain.User;
import com.example.toyproject.dto.UserDto;
import com.example.toyproject.response.UserListResponse;
import com.example.toyproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserDto userDto) {
        // UserService를 통해 데이터베이스에 저장
        userService.saveUser(userDto);

        // 정상적으로 저장된 경우 응답 코드 201 반환
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 완료되었습니다.");
    }
    @GetMapping("/list")
    public ResponseEntity<UserListResponse> getUserList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String sort) {

        // userService를 호출하여 이름순으로 정렬된 회원 목록을 조회합니다.
        Page<User> userPage = userService.getUserListOrderedByName(page - 1, pageSize);

        // 회원 목록 조회 결과를 사용하여 응답 생성
        UserListResponse response = new UserListResponse(userPage.getContent(), page, pageSize, sort);
        return ResponseEntity.ok(response);
    }

}
