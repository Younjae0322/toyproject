package com.example.toyproject.controllerTest;

import com.example.toyproject.controller.UserController;
import com.example.toyproject.domain.User;
import com.example.toyproject.dto.UserDto;
import com.example.toyproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    // UserService를 @MockBean으로 주입
    //@MockBean
    @Autowired
    private UserService userService;


    @Test
    @Transactional
    void testJoin() throws Exception{
        // Given
        // user 객체 생성 후 저장 작업
        UserDto userDto = new UserDto();
        userDto.setPassword("1234");
        userDto.setNickname("홍박사");
        userDto.setName("홍길동");
        userDto.setPhoneNumber("010-1234-1234");
        userDto.setAddress("경기도");
        userService.saveUser(userDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userDto);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // Then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // mockMvc에 대한 결과 호출
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string("회원 가입이 완료되었습니다."));

    }
    @Test
    @Transactional
    @DisplayName("데이터 삽입 테스트")
    void addUser() throws Exception {

        // 50개의 사용자 데이터를 추가
        IntStream.rangeClosed(1, 50).forEach(i -> {
            UserDto userDto = new UserDto();
            userDto.setPassword("password" + i);
            userDto.setNickname("nickname" + i);
            userDto.setName("name" + i);
            userDto.setPhoneNumber("phoneNumber" + i);
            userDto.setAddress("address" + i);
            userService.saveUser(userDto);
        });

    }

    @Test
    @Transactional
    @DisplayName("페이징 처리 후 이름순 정렬 출력")
    void testGetUserList() throws Exception {

        // 사용자 목록 조회 테스트
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/list")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .param("sort", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // mockMvc에 대한 결과 호출
                .andDo(print())
                // 페이지와 페이지크기에 대한 검증
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(10))
                // 이하 추가적인 필드에 대한 검증을 수행할 수 있습니다.
                .andExpect(MockMvcResultMatchers.jsonPath("$.userList").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userList.length()").value(10)); // 페이지당 10개씩 조회하므로 예상되는 사용자 목록의 길이는 10입니다.
    }


    @Test
    @Transactional
    @DisplayName("사용자 정보 수정 테스트")
    void testUpdateUser() throws Exception {
        // Given
        int userId = 41;
        UserDto updatedUserDto = new UserDto();
        //updatedUserDto.setId(userId);
        updatedUserDto.setPassword("newPassword1");
        updatedUserDto.setNickname("newNickname");
        updatedUserDto.setName("newName");
        updatedUserDto.setPhoneNumber("newPhoneNumber");
        updatedUserDto.setAddress("newAddress");

        // Perform the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // mockMvc에 대한 결과 호출
                .andDo(print());

        // Then
        assertThat(userService.isUserUpdated()).isTrue();
    }



}
