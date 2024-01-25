package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.AdditemtoCartrequest;
import com.lcwd.electronic.store.dtos.ApiresponceMessage;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;



    @PostMapping("/{userid}")
    public ResponseEntity<CartDto> additemtpcart(@PathVariable("userid") String userid, @RequestBody AdditemtoCartrequest cartrequest){
        CartDto additemtocart = cartService.additemtocart(userid, cartrequest);
        return  new ResponseEntity<>(additemtocart, HttpStatus.OK);

    }

    // remove item from cart

    @DeleteMapping ("/{userid}/item/{itemid}")
    public ResponseEntity<ApiresponceMessage> removefromcart(@PathVariable("itemid") int itemid,@PathVariable("userid") String userid){
        cartService.removeitemfromcart(userid,itemid);
        ApiresponceMessage build = ApiresponceMessage.builder().massage("item is removed ").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(build,HttpStatus.OK);

    }


    @DeleteMapping ("/{userid}")
    public ResponseEntity<ApiresponceMessage> clearcart(@PathVariable("userid") String userid){
        cartService.crearcart(userid);
        ApiresponceMessage build = ApiresponceMessage.builder().massage(" now cart is blank  ").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(build,HttpStatus.OK);

    }


    @GetMapping("/{userid}")
    public ResponseEntity<CartDto> getcart(@PathVariable("userid") String userid){
        CartDto cartByUser = cartService.getCartByUser(userid);
        return  new ResponseEntity<>(cartByUser, HttpStatus.OK);

    }

}
