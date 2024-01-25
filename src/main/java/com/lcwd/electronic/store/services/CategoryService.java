package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponce;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    // create
 public CategoryDto create(CategoryDto categoryDto);
    //update
public CategoryDto update(CategoryDto categoryDto,String categoryid);
    // getall category

    PageableResponce<CategoryDto> getall(int pagenumber,int pagesize,String sortBy,String sortDir);
    // delete
    void delete(String categoryid) throws IOException;

    // getbyid

    CategoryDto getcategoryByid(String categoryid);

    List<CategoryDto> searchby(String keyword);


}
