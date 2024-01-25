package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productid;

    private String title;

    private String description;

    private int price;

    private int quantity;

    private Date addeddate;

    private boolean live;

    private boolean stock;

    private  int discountedprice;

    private String productimagename;
    private CategoryDto category;

}
