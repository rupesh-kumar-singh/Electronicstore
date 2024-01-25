package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    //create
    public ProductDto  createproduct(ProductDto productDto);



    //update

    public  ProductDto updateproduct(ProductDto productDto,String productid);

    //delete
    public void deleteproduct(String productid) throws IOException;
    // getsingleproduct

    public ProductDto getsingleproduct(String productid);
    // getallproduct

    public PageableResponce<ProductDto> getall(int pagenumber,int pagesize,String sortBy,String sortDir);


    public PageableResponce<ProductDto> getAllive(int pagenumber,int pagesize,String sortBy,String sortDir);

    // searchapiimplementation

    public PageableResponce<ProductDto> searchbytitle(String subtitle, int pagenumber, int pagesize, String sortBy, String sortDir);

    // create product with category

    ProductDto createwithCategory(ProductDto productDto, String categoryid);

    //
    ProductDto updateCategory(String productid,String categoryid);

// getting only same category product

    PageableResponce<ProductDto> gettingalldatafromsamecateogry( String categoryid, int pagenumber, int pagesize, String sortBy, String sortDir);


}
