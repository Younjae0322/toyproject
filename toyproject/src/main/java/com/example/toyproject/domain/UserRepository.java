package com.example.toyproject.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.SortedMap;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


}
