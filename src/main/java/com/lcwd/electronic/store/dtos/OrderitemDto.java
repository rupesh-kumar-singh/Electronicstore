package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderitemDto {


    private int orderid;

    private int quantity;
    private int totalprice;

    private ProductDto product;

    // not need to send did not need
//    private Order order;
}
