package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDto {


    private String userId;


    @Size(min = 3,max = 15,message = "invalid name")
    private String name;

      @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "invalid user email")
    @NotBlank(message = "field is required !!!")
    private String email;


    @NotBlank(message = "password required")
    private String password;
    @Size(min = 4,max = 6,message = "Invalid data !!!")
    private String gender;

@NotBlank(message = "write something in about section")
    private String about;

     // custom validator
    // pattern will learn

    @ImageNameValid(message = "pleasee give image name")
    private String imagename;


    private Set<RoleDto> roles = new HashSet<>();
}
