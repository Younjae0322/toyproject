package com.example.toyproject.response;

import com.example.toyproject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {
    private List<User> userList;
    private int page;
    private int pageSize;
    private String sort;
}
