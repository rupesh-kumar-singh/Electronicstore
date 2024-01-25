package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiresponceMessage;
import com.lcwd.electronic.store.dtos.ImageResponce;
import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.UserService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
   private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
 private UserService userService;

    @Autowired
    private FileService fileService;

    private String imageuploadpath;
    // create
    @PostMapping
    public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto){
        UserDto userdto = userService.createUser(userDto);
        return new ResponseEntity<>(userdto, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{userid}")
    public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userDto, @PathVariable("userid") String userid){

        UserDto userDto1 = userService.updateUser(userDto, userid);

        return  new ResponseEntity<>(userDto1,HttpStatus.OK);
    }
    // delete
    @DeleteMapping("/{userid}")
    public ResponseEntity<ApiresponceMessage> deleteuser(@PathVariable("userid") String userid) throws IOException {
        ApiresponceMessage massage = ApiresponceMessage.builder().massage("user is deleted succesfully").success(true).status(HttpStatus.OK).build();
        userService.deleteUser(userid);
        return  new ResponseEntity<>(massage,HttpStatus.OK);
    }

    // get all
    @GetMapping
    public  ResponseEntity<PageableResponce<UserDto>> getalluser(@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
                                                        @RequestParam(value = "pagesize",defaultValue = "10",required = false) int pagesize,
                                                        @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
                                                        @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
                                                     ){
        return new ResponseEntity<>(userService.getallUser(pagenumber,pagesize,sortBy,sortDir),HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userid}")
    public ResponseEntity<UserDto> getuser(@PathVariable("userid") String userid){
        return new ResponseEntity<>(userService.getuserById(userid),HttpStatus.OK);

    }

    //getby email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto>  getbyuseremail(@PathVariable("email") String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    // getby search

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getuserByemail(@PathVariable("keyword") String keyword){
            return new ResponseEntity<>(userService.searchuser(keyword), HttpStatus.OK);

    }

    // upload your image

    @PostMapping("/image/{userid}")
    public  ResponseEntity<ImageResponce> uploadUserImage(@PathVariable("userid") String userid,@RequestParam("userimage") MultipartFile image) throws IOException {
        String imagename = fileService.uploadFile(image, imageuploadpath);
        UserDto user = userService.getuserById(userid);
        user.setImagename(imagename);
        userService.updateUser(user,userid);
        ImageResponce imageResponce = ImageResponce.builder().imageName(imagename).massage("uploded succesfully!!").success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponce,HttpStatus.OK);
    }
    //serve user image

    @GetMapping("/image/{userid}")
    public void serverUserImage(@PathVariable("userid") String userid, HttpServletResponse response) throws IOException {
        UserDto userDto = userService.getuserById(userid);
        logger.info("userdtoimage name{}",userDto.getImagename());
        InputStream resources = fileService.getresources(imageuploadpath, userDto.getImagename());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resources,response.getOutputStream());
    }
}
