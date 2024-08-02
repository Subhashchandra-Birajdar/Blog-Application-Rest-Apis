package com.Blog_App_Apis.service;

import com.Blog_App_Apis.Payload.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

    List<UserDto> getAllUsers(Pageable pageable);

    List<UserDto> getAllUsers1(int page, int size, String sortField, String sortDirection);

}