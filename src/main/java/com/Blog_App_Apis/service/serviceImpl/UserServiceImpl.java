package com.Blog_App_Apis.service.serviceImpl;

import com.Blog_App_Apis.Payload.UserDto;
import com.Blog_App_Apis.entity.User;
import com.Blog_App_Apis.exception.ResourceNotFoundException;
import com.Blog_App_Apis.repository.UserRepository;
import com.Blog_App_Apis.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepositoy;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userdto) {
        User userdata = this.dtoToUser(userdto); // dto to entity data save, get in userdata ref
        User saveuser = this.userRepositoy.save(userdata);     // save in userrepository
        UserDto userDto1 = this.userToDto(saveuser); // Now, convert user to userDto for Response data purpose
        return userDto1; // return the UserDto data
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        //finding the user with userid
        Optional<User> byId = this.userRepositoy.findById(userId);
         // if userid will get then return to the user
        //User user = byId.get();
        User user = byId.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        // when user get then we set the value mean update the user
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        //after updating user save in database table through - Jpa hibernate
        User updatedUser = this.userRepositoy.save(user);
        // convert UserDto to UserEntity
        UserDto userDto2 = this.userToDto(updatedUser);
        //return user UserDto - return(go) to controller
        return userDto2;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        //find user if user find it will return otherwise it will throw exception ResourceNotFoundException
        User user = userRepositoy.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        UserDto userDto3 = this.userToDto(user);   // create UserDto obj add into user data
        return userDto3; // return UserDtos
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepositoy.findAll();
        //here one by one user will get through stream and store in list
        List<UserDto> userDtos4 = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDtos4;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepositoy.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));;
        // this.userRepositoy.deleteById(userId);
        this.userRepositoy.delete(user); // delete/deleteById we can use anyone here
    }

//    private UserDto userToDto(User user){
//        UserDto userdto = new UserDto();
//        userdto.setId(user.getId());
//        userdto.setName(user.getName());
//        userdto.setEmail(user.getEmail());
//        userdto.setPassword(user.getPassword());
//        userdto.setAbout(user.getAbout());
//        return userdto;
//    }

    private UserDto userToDto(User user){
        UserDto userdto = modelMapper.map(user,UserDto.class); //used modelmapper
        return userdto;
    }
//    private User dtoToUser(UserDto userDto){
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
//        return user;
//    }

    private User dtoToUser(UserDto userDto){
        User user = modelMapper.map(userDto,User.class); // used modelmapper also we can use modelstruct here
        return user;
    }

    @Override
    public List<UserDto> getAllUsers(Pageable pageable) {
        //Pageable p = PageRequest.of(0, 10); // here get the pageable class here we get pagesize,pagenumber
        Page<User> all = userRepositoy.findAll(pageable);// pageable return the page
        List<User> content = all.getContent();
        List<UserDto> userDtos = content.stream().map((a) -> modelMapper.map(a, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public List<UserDto> getAllUsers1(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> all = userRepositoy.findAll(pageable);
        List<User> userList = all.getContent();
        List<UserDto> userDtos = userList.stream().map((a) -> modelMapper.map(a, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

}

