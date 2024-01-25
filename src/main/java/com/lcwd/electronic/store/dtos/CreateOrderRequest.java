package com.lcwd.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {


    @NotBlank(message = "cartid is required")
private String cartid;

    @NotBlank(message = "userid is required")
private String usertid;

    private String oderstatus="pending";

    // not paid,paid
    private String paymentstatus="notpaid";

    @NotBlank(message = "address is required")
    private  String billingaddress;


    @NotBlank(message = "phone is required")
    private String billingphone;

    @NotBlank(message = "valid name is required")
    private String billingname;



    //  private UserDto user;





}
