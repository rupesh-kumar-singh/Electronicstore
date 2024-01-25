package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AdditemtoCartrequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {
    // add item to cart


    // cart for user is not available we will create cart and then add the itme
    // if cart id avaible then the  add the item
    CartDto additemtocart(String userid, AdditemtoCartrequest cartrequest);
    // remove item to cart
    void removeitemfromcart(String userid,int cartitem);

    void  crearcart(String userid);
    CartDto getCartByUser(String userid);

}
