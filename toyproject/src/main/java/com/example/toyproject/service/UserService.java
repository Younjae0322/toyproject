package com.example.toyproject.service;

import com.example.toyproject.domain.User;
import com.example.toyproject.domain.UserRepository;
import com.example.toyproject.dto.UserDto;
import com.example.toyproject.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;

    public User findUser(Integer id){ return userRepository.findById(id).get();}

    public UserDto findUserId(Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return UserDto.fromEntity(user);
    }

    // userDto를 Entity로 변환 Repository에서 DB저장
    public void saveUser(UserDto userDto){
        User user = User.createUser(userDto);
        userRepository.save(user);
    }
}
