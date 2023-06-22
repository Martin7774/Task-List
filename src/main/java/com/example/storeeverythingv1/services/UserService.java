package com.example.storeeverythingv1.services;


import com.example.storeeverythingv1.data.User;
import com.example.storeeverythingv1.data.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
