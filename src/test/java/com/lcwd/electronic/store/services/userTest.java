package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.Table;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class userTest {


    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private RoleRepository roleRepository;
    User user;
    Role role;
    String roleId;
    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public  void init(){

        role  = Role.builder().roleId("abc").roleName("normal").build();

        user = User.builder().name("ruedd").email("singh.rupesh7999@gmail.com").about("this is tdidnfd testing").
                gender("male").imagename("abc.png").password("dffrf").roles(Set.of(role)).build();

        roleId= "abc";
    }


    @Test
    public  void updateuser_test(){

String userid = "fdfv";
        UserDto userDto = UserDto.builder().name("ram kumar singh").about("this is tdidnfd testing").
                gender("male").imagename("abc.png").build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updateUser = userService.updateUser(userDto, userid);

        System.out.println(updateUser.getName());
        Assertions.assertNotNull(userDto);

    }

}
