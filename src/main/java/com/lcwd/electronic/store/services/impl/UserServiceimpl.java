package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.Resourcenotfoundexception;
import com.lcwd.electronic.store.helper.Healper;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceimpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
private RoleRepository roleRepository;


    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagepath;

@Value("${role_normal_id}")
private String normalRoleId;



    private Logger logger = LoggerFactory.getLogger(UserServiceimpl.class);
    @Override
    public UserDto createUser(UserDto userdto) {

        // generate unique id instring format
        String userid = UUID.randomUUID().toString();
        userdto.setUserId(userid);

        userdto.setPassword(userdto.getPassword());

//        dto-entity
          User user =  dtoToEntity(userdto);

//          fetch role of normal user and set it to user
        Role role = roleRepository.findById(normalRoleId).get();
        user.getRoles().add(role);
        User save = userRepository.save(user);
//        entity-dto
              UserDto newdto  = entityToDto(save);
        return  newdto;
    }



    @Override
    public UserDto updateUser(UserDto userdto, String userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new Resourcenotfoundexception("user not found"));
        user.setAbout(userdto.getAbout());
//        user.setUserId(userid);
        user.setGender(userdto.getGender());
     user.setName(userdto.getName());
     user.setPassword(userdto.getPassword());
        user.setImagename(userdto.getImagename());
        User user1 = userRepository.save(user);

        return entityToDto(user);
    }

    @Override
    public void deleteUser(String userid) {

        User user = userRepository.findById(userid).orElseThrow(() -> new Resourcenotfoundexception("user not found"));
      //delete user image
        String fullpath = imagepath + user.getImagename();


        try {
            Path path = Paths.get(fullpath);
            Files.delete(path);
        }catch (NoSuchFileException e){
logger.info("not fount file {}",e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        userRepository.delete(user);
    }

    @Override
    public PageableResponce<UserDto> getallUser(int pagenumber, int pagesize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy)).descending():(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pagenumber,pagesize,sort);

        Page<User> page = userRepository.findAll(pageable);
        PageableResponce<UserDto> responce = Healper.getPeableResponce(page, UserDto.class);
        return responce;
    }

    @Override
    public UserDto getuserById(String userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new Resourcenotfoundexception("user not found"));
        return   entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByemail(email).orElseThrow(() -> new Resourcenotfoundexception("not found"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchuser(String keyword) {
        List<User> bynameContaining = userRepository.findBynameContaining(keyword);
        List<UserDto> collectdto = bynameContaining.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());
        return collectdto;
    }

    @Override
    public Optional<User> findUserByEmailforgoogleauth(String email) {

        return userRepository.findByemail(email);
    }

    private UserDto entityToDto(User save) {
//        UserDto userDto = UserDto.builder().userId(save.getUserId())
//                .name(save.getName())
//                .email(save.getEmail())
//                .about(save.getAbout())
//                .gender(save.getGender())
//                .imagename(save.getImagename())
//                .password(save.getPassword()).build();
        return mapper.map(save, UserDto.class);
    }

    private User dtoToEntity(UserDto userdto) {
//        User user = User.builder().userId(userdto.getUserId())
//                .name(userdto.getName())
//                .email(userdto.getEmail())
//                .password(userdto.getPassword())
//                .about(userdto.getAbout())
//                .gender(userdto.getGender())
//                .imagename(userdto.getImagename())
//                .build();



        return mapper.map(userdto,User.class);
    }
}
