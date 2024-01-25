package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.entities.*;
import com.lcwd.electronic.store.exceptions.BadapiRequest;
import com.lcwd.electronic.store.exceptions.Resourcenotfoundexception;
import com.lcwd.electronic.store.helper.Healper;
import com.lcwd.electronic.store.repositories.CartReposetory;
import com.lcwd.electronic.store.repositories.OrderReposetory;
import com.lcwd.electronic.store.repositories.OrderitemReposetory;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OrderServiceimpl implements OrderService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderReposetory orderReposetory;
    @Autowired
    private OrderitemReposetory orderitemReposetory;

    @Autowired
    private CartReposetory cartReposetory;
    @Autowired
    private ModelMapper  mapper;

    @Override
    public OrderDto createorder(CreateOrderRequest orderDto) {
        String userid =  orderDto.getUsertid();
        String cartid = orderDto.getCartid();
        // fetch th euser
        User user = userRepository.findById(userid).orElseThrow(() ->
                new Resourcenotfoundexception("user is not available"));
        // fetch the cart
        Cart cart = cartReposetory.findById(cartid).orElseThrow(() ->
                new Resourcenotfoundexception("cart is not avalable"));
        List<Cartitem> cartitems = cart.getCartitems();
        if (cartitems.size()<=0){
            throw  new BadapiRequest("invalid number of item in cart");
        }

        Order order = Order.builder()
                .billingname(orderDto.getBillingname())
                .billingphone(orderDto.getBillingphone())
                .billingaddress(orderDto.getBillingaddress())
                .oderdate(new Date())
                .diliverdate(null)
                .paymentstatus(orderDto.getPaymentstatus())
                .oderstatus(orderDto.getOderstatus())
                .orderid(UUID.randomUUID().toString()).user(user).build();
//        orderitem
        // or amount

        AtomicReference<Integer> orderamount = new  AtomicReference<>(0);
        List<Orderitem> orderitems = cartitems.stream().map((cartitem) -> {
            // cartitem-> orderitem
            Orderitem orderitem = Orderitem.builder()
                    .quantity(cartitem.getQuantity())
                    .product(cartitem.getProduct())
                    .totalprice(cartitem.getQuantity() * cartitem.getProduct()
                            .getDiscountedprice()).order(order).build();

            orderamount.set(orderamount.get()+ orderitem.getTotalprice());
            return orderitem;
        }).collect(Collectors.toList());
        order.setOrderitem(orderitems);
        order.setOrderamount(orderamount.get());
        cart.getCartitems().clear();
        cartReposetory.save(cart);
        Order savedorder = orderReposetory.save(order);

        return mapper.map(savedorder,OrderDto.class);
    }

    @Override
    public void removeorder(String orderid) {
        Order order = orderReposetory.findById(orderid).orElseThrow(() ->
                new Resourcenotfoundexception("order not found"));
        orderReposetory.delete(order);

    }

    @Override
    public List<OrderDto> getorderofuser(String userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new Resourcenotfoundexception("user is not avalabie"));


        List<Order> orders = orderReposetory.findByUser(user);
        List<OrderDto> orderDtoList = orders.stream().map((order) -> {
            return mapper.map(order, OrderDto.class);
        }).collect(Collectors.toList());
        return orderDtoList;
    }

    @Override
    public PageableResponce<OrderDto> getorder(int pagenumber, int pagesize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pagenumber,pagesize,sort);
        Page<Order> all = orderReposetory.findAll(pageable);
        PageableResponce<OrderDto> peableResponce = Healper.getPeableResponce(all, OrderDto.class);
        return peableResponce;
    }
}
