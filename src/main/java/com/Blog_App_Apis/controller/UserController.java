package com.Blog_App_Apis.controller;

import com.Blog_App_Apis.Payload.UserDto;
import com.Blog_App_Apis.exception.ApiResponse;
import com.Blog_App_Apis.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser") // http://localhost:8080/api/users/addUser
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto CreateuserDto1 = this.userService.createUser(userDto);
        return new ResponseEntity<>(CreateuserDto1, HttpStatus.CREATED);
    }

    /*
    Json:
    Post : http://localhost;8080/api/users/addUser
    {
    "name" : "sachin patil",
    "email" : "sachinpatil@gmail.com",
    "password" : "sachin",
    "about" : "sachin is developer"
    }
     */

    @PutMapping("/{userId}")  //http://localhost:8080/api/users/1
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable("userId") Integer Id)
    {
        UserDto updateduserDto1 = this.userService.updateUser(userDto, Id);
        //return ResponseEntity.ok(updateduserDto1);
        return  new ResponseEntity<>(updateduserDto1,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}") //http://localhost:8080/api/users/1
    public ResponseEntity<ApiResponse> deleteuser(
            @PathVariable ("userId")Integer Id)
    {
        this.userService.deleteUser(Id);
        return new ResponseEntity<>(
                new ApiResponse("User deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/") //http://localhost:8080/api/users/
    public ResponseEntity<List<UserDto>> getallusers(){
        List<UserDto> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @GetMapping("/{userId}") //http://localhost:8080/api/users/1
    public ResponseEntity<UserDto> getSingleuser(
            @PathVariable("userId") Integer uId)
    {
        UserDto getoneuser = this.userService.getUserById(uId);
        //return ResponseEntity.ok(getoneuser);
        return new ResponseEntity<>(getoneuser,HttpStatus.OK);
    }

    @GetMapping("/pagination/users") // http://localhost:8080/api/users/pagination/users?page=0&size=2o
    public List<UserDto> getUsers(Pageable pageable) {
        List<UserDto> allUsers = userService.getAllUsers(pageable);
        return allUsers;
    }

    @GetMapping("/pagination")
    // http://localhost:8080/api/users/pagination?page=0&size=3&sortField=name&sortDirection=desc
    public List<UserDto> getUsersDir(
            @RequestParam(value="page",defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection)
    {
        List<UserDto> allUsers = userService.getAllUsers1(page,size,sortField,sortDirection);
        return allUsers;
    }

}
