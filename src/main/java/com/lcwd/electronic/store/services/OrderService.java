package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponce;

import java.util.List;

public interface OrderService {
//    create order

    OrderDto createorder(CreateOrderRequest orderDto);
    // remove order
    void removeorder(String orderid);
    // get order from user
    List<OrderDto> getorderofuser(String userid);
    // get order by admin
    PageableResponce<OrderDto>  getorder(int pagenumber,int pagesize,String sortBy, String sortDir);
    // orthers method releted to order

}
