package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Cartitem;
import com.lcwd.electronic.store.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartid;
    private Date createdat;

    private UserDto user;


    private List<CartitemDto> cartitems = new ArrayList<>();

}
