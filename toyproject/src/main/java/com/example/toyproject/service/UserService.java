package com.example.toyproject.service;

import com.example.toyproject.domain.User;
import com.example.toyproject.domain.UserRepository;
import com.example.toyproject.dto.UserDto;
import com.example.toyproject.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;

    public User findUser(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new NoSuchElementException("해당 "+ id + "회원이 존재하지 않습니다."));}

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
    // 회원목록 조회
    public Page<User> getUserListOrderedByName(int page, int pageSize) {

        // 이름으로 오름차순 정렬되도록 정렬 객체 생성
        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return userRepository.findAll(pageable);
    }
}
