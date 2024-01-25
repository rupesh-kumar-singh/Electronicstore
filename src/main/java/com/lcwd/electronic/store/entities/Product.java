package com.lcwd.electronic.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String productid;

    private  String title;

    @Column(length = 10000)
    private  String description;

    private int price;
    private int quantity;
    private Date addeddate;
    private boolean live;
    private boolean stock;
private  int discountedprice;
private String productimagename;
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "category_id")
private Category category;

}
