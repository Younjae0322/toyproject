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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String password;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String address;


    public void updateUser(UserDto userDto){
        this.nickname = userDto.getNickname();
        this.password = userDto.getPassword();
        this.name = userDto.getName();
        this.phoneNumber = userDto.getPhoneNumber();
        this.address = userDto.getAddress();
    }

    public static User createUser(UserDto userDto){
        User user = User.builder()
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber())
                .address(userDto.getAddress())
                .build();
        return user;
    }
}
