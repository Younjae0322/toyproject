package com.example.toyproject.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.SortedMap;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //회원 목록을 지정된 정렬 기준에 따라 조회합니다
    List<User> findAll(Sort sort);


}
