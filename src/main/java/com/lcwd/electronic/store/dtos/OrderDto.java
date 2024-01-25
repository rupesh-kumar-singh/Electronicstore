package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Orderitem;
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
@ToString
public class OrderDto {

    private  String orderid;

    private String oderstatus="pending";
    private String paymentstatus="notpaid";
    private int orderamount;
    private  String billingaddress;
    private String billingphone;
    private String billingname;
    private Date oderdate = new Date();

    private  Date diliverdate;


//    private UserDto user;


    private List<OrderitemDto> orderitem =  new ArrayList<>();



}
