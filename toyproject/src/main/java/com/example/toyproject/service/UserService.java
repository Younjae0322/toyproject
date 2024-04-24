package com.example.toyproject.service;

import com.example.toyproject.domain.User;
import com.example.toyproject.domain.UserRepository;
import com.example.toyproject.dto.UserDto;
import com.example.toyproject.exception.NotFoundException;
import com.example.toyproject.exception.UserNotFoundException;
import lombok.Getter;
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

    @Getter
    private boolean userUpdated = false;
    
    // id를 조회해서 회원 정보 확인, id에 정보 없으면 예외출력
    // 한명만 조회하는 경우에 사용하려고 만듦.
    public User findUser(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new NoSuchElementException("해당 "+ id + "회원이 존재하지 않습니다."));}


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
        // findAll() 메소드 호출 JPA 라이브러리에서 사용 가능.
        return userRepository.findAll(pageable);
    }



    public void updateUser(Integer userId,UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("해당하는 사용자가 존재하지 않습니다.");
        }

        // 새로운 정보로 사용자 업데이트
        User existingUser = optionalUser.get();
        existingUser.setPassword(userDto.getPassword());
        existingUser.setNickname(userDto.getNickname());
        existingUser.setName(userDto.getName());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        existingUser.setAddress(userDto.getAddress());

        // 업데이트된 사용자 정보 저장 및 반환
        userRepository.save(existingUser);

        userUpdated = true;
    }

}

