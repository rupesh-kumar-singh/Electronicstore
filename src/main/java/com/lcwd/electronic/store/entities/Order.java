package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private  String orderid;
    // pending ,dispatch,delivered
    private String oderstatus;

    // not paid,paid
    private String paymentstatus;

    private int orderamount;

    @Column(length = 1000)
    private  String billingaddress;


    private String billingphone;

    private String billingname;

    private Date oderdate;

    private  Date diliverdate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private  User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Orderitem> orderitem =  new ArrayList<>();





}
