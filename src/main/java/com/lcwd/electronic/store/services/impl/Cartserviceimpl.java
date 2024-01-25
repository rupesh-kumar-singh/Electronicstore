package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.AdditemtoCartrequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.Cartitem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.BadapiRequest;
import com.lcwd.electronic.store.exceptions.Resourcenotfoundexception;
import com.lcwd.electronic.store.repositories.CartReposetory;
import com.lcwd.electronic.store.repositories.CartitemReposetroy;
import com.lcwd.electronic.store.repositories.ProductReposetory;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Service
public class Cartserviceimpl implements CartService {
    @Autowired
    private ProductReposetory productReposetory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartReposetory cartReposetory;

    @Autowired
    private CartitemReposetroy cartitemReposetroy;
    @Autowired
    private ModelMapper mapper;
    @Override
    public CartDto additemtocart(String userid, AdditemtoCartrequest cartrequest) {
        int quantity = cartrequest.getQuantity();
        String productid = cartrequest.getProductid();

        if (quantity<=0){

            throw new BadapiRequest("requested quantity is not valid");
        }
        // getthe proo
        Product product = productReposetory.findById(productid).orElseThrow(() -> new Resourcenotfoundexception("not available "));
        //fetch the user
        User user = userRepository.findById(userid).orElseThrow(() -> new Resourcenotfoundexception("user is not avalible "));
        Cart cart = null;
        try {
            cart = cartReposetory.findByUser(user).get();
        }catch (NoSuchElementException e){
            cart = new Cart();
            cart.setCartid(UUID.randomUUID().toString());
        }
        cart.setCreatedat(new Date());
        // if cart item already present then update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<Cartitem> items = cart.getCartitems();
        items = items.stream().map(item -> {
            if (item.getProduct().getProductid().equals(productid)) {
                //  item already present in cart
                item.setQuantity(quantity);
                item.setTotalprice(quantity*product.getDiscountedprice());
                  updated.set(true);
            }

            return item;
        }).collect(Collectors.toList());

//        cart.setCartitems(updateditem);


        // create item
    if (!updated.get()){
        Cartitem cartitem = Cartitem.builder().quantity(quantity).totalprice(quantity * product.getPrice()).cart(cart).product(product).build();
        cart.getCartitems().add(cartitem);



    }
        cart.setUser(user);
        Cart updatedcart = cartReposetory.save(cart);
        return mapper.map(updatedcart,CartDto.class);
    }

    @Override
    public void removeitemfromcart(String userid, int cartitem) {

        Cartitem cartitem1 = cartitemReposetroy.findById(cartitem).orElseThrow(() -> new Resourcenotfoundexception("carti is not avilable"));
        cartitemReposetroy.delete(cartitem1);
    }

    @Override
    public void crearcart(String userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new Resourcenotfoundexception("user is not avalible "));
        Cart cart = cartReposetory.findByUser(user).orElseThrow(() -> new Resourcenotfoundexception("cart of given user not avaliable"));
        cart.getCartitems().clear();
        cartReposetory.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userid) {

        User user = userRepository.findById(userid).orElseThrow(() -> new Resourcenotfoundexception("user is not avalible "));
        Cart cart = cartReposetory.findByUser(user).orElseThrow(() -> new Resourcenotfoundexception("cart of given user not avaliable"));

        return mapper.map(cart,CartDto.class);
    }
}
