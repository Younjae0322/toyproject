package com.example.toyproject.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.SortedMap;

@Repository
// JPA를 사용하기 위해 Repository를 상속받음
public interface UserRepository extends JpaRepository<User, Integer> {


}
