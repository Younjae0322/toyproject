package com.example.toyproject.dto;

import com.example.toyproject.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private int id;

    private String password;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String address;

    public static UserDto fromEntity(User user){
        return UserDto.builder()
                .password(user.getPassword())
                .nickname(user.getNickname())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }
}
