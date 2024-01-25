package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.exceptions.Resourcenotfoundexception;
import com.lcwd.electronic.store.helper.Healper;
import com.lcwd.electronic.store.repositories.CategoryReposetory;
import com.lcwd.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class Categoryserviceimpl implements CategoryService {

    @Autowired
    private CategoryReposetory categoryReposetory;
private Logger logger = LoggerFactory.getLogger(Categoryserviceimpl.class);
    @Value("${category.profile.image.path}")
    private String imagepath;

    @Autowired
    private ModelMapper mapper;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        // creating category id
        String categoryid = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryid);
        Category category = mapper.map(categoryDto, Category.class);
        Category save = categoryReposetory.save(category);
        CategoryDto categoryDto1 = mapper.map(save, CategoryDto.class);

        return categoryDto1;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryid) {


        Category category = categoryReposetory.findById(categoryid).orElseThrow(() -> new  Resourcenotfoundexception("Category not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category category1 = categoryReposetory.save(category);
      return   mapper.map(category1,CategoryDto.class);


    }

    @Override
    public PageableResponce<CategoryDto> getall(int pagenumber,int pagesize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?  (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pagenumber,pagesize,sort);
        Page<Category> page = categoryReposetory.findAll(pageable);
        PageableResponce<CategoryDto> peableResponce = Healper.getPeableResponce(page, CategoryDto.class);

        return peableResponce;
    }

    @Override
    public void delete(String categoryid)  {
        Category category = categoryReposetory.findById(categoryid).orElseThrow(() -> new  Resourcenotfoundexception("Category not found"));
                    String fullname =   imagepath + category.getCoverImage();

                    try {
                        Path path = Paths.get(fullname);
                        Files.delete(path);
                    }catch (NoSuchFileException e){
                        logger.info("not exist{}",e);
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }

        categoryReposetory.delete(category);
    }

    @Override
    public CategoryDto getcategoryByid(String categoryid) {
        Category category = categoryReposetory.findById(categoryid).orElseThrow(() -> new  Resourcenotfoundexception("Category not found"));
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    @Override
    public List<CategoryDto> searchby(String keyword) {
        List<Category> categories = categoryReposetory.findBytitleContaining(keyword);

        List<CategoryDto> collect = categories.stream().map((item) -> mapper.map(item, CategoryDto.class)).collect(Collectors.toList());
        return collect;
    }
}
