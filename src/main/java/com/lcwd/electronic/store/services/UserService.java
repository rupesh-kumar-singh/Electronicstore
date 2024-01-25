package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    //create user
    UserDto createUser(UserDto userdto);
    //update user

    UserDto updateUser(UserDto userdto, String userid);

    // delete user
    void deleteUser(String userid) throws IOException;

    //get all user
    PageableResponce<UserDto> getallUser(int pagenumber, int pagesize, String sortBy, String sortDir);

    // get single user by id
    UserDto getuserById(String userid);
    // get single user bt user

    UserDto getUserByEmail(String email);
    // seach user

    List<UserDto> searchuser(String keyword);
    // other user secific feature user

    Optional<User> findUserByEmailforgoogleauth(String email);

}
