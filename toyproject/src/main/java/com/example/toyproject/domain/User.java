package com.example.toyproject.domain;

import com.example.toyproject.dto.UserDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@AllArgsConstructor
// ORM구조로 데이터베이스에 Table생성을 담당.
public class User {

    @Id // primary key
    // auto increment 자동 증가값 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private int id;

    private String password;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String address;

    
    // test case에서 데이터 수정 , 현재는 사용하지 않는 메소드
    public void updateUser(UserDto userDto){
        this.nickname = userDto.getNickname();
        this.password = userDto.getPassword();
        this.name = userDto.getName();
        this.phoneNumber = userDto.getPhoneNumber();
        this.address = userDto.getAddress();
    }

    public static User createUser(UserDto userDto){
        return User.builder()
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber())
                .address(userDto.getAddress())
                .build();
    }
}
