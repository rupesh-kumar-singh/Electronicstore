package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "order_item")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orderitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderid;

    private int quantity;
    private int totalprice;
    @OneToOne
    @JoinColumn(name ="product_id" )
    private  Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
