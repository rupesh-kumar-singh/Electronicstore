package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiresponceMessage;
import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createorder(@Valid @RequestBody CreateOrderRequest createOrderRequest){
        OrderDto orderDto = orderService.createorder(createOrderRequest);
        return  new ResponseEntity<>(orderDto, HttpStatus.CREATED);

    }

    @DeleteMapping("/{orderid}")
    public ResponseEntity<ApiresponceMessage> removeorder(@PathVariable("orderid") String orderid){

        orderService.removeorder(orderid);
        ApiresponceMessage build = ApiresponceMessage.builder().status(HttpStatus.OK).success(true).massage("order id deleted").build();
        return new ResponseEntity<>(build,HttpStatus.OK);
    }


@GetMapping("/user{userid}")
    public ResponseEntity<List<OrderDto>> getorderofuser(@PathVariable("userid") String  userid){
    List<OrderDto> getorderofuser = orderService.getorderofuser(userid);
    return  new ResponseEntity<>(getorderofuser,HttpStatus.OK);
}

@GetMapping
    public ResponseEntity<PageableResponce<OrderDto>> getorder(  @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,

                                                                @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
                                                                @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
    PageableResponce<OrderDto> getorder = orderService.getorder(pagenumber, pagesize, sortBy, sortDir);
    return  new ResponseEntity<>(getorder,HttpStatus.OK);
}



}
