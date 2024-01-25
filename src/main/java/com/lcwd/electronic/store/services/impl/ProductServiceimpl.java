package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.Resourcenotfoundexception;
import com.lcwd.electronic.store.helper.Healper;
import com.lcwd.electronic.store.repositories.CategoryReposetory;
import com.lcwd.electronic.store.repositories.ProductReposetory;
import com.lcwd.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductReposetory productReposetory;

    @Autowired
    private CategoryReposetory categoryReposetory;
    @Value("${product.image.path}")
    private String imagepath;

    @Autowired
    private ModelMapper mapper;
    @Override
    public ProductDto createproduct(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        // rpoduct id
        String productid = UUID.randomUUID().toString();
        product.setProductid(productid);
        product.setAddeddate(new Date());
        Product save = productReposetory.save(product);
        ProductDto map = mapper.map(save, ProductDto.class);
        return map;
    }

    @Override
    public ProductDto updateproduct(ProductDto productDto, String productid) {
        Product product = productReposetory.findById(productid).orElseThrow(() -> new Resourcenotfoundexception("product not avaiable"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedprice(productDto.getDiscountedprice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductimagename(productDto.getProductimagename());
        Product save = productReposetory.save(product);
        return mapper.map(save,ProductDto.class);
    }

    @Override
    public void deleteproduct(String productid) throws IOException {
        Product product = productReposetory.findById(productid).orElseThrow(() -> new Resourcenotfoundexception("product not avaiable"));
        String fullpath = imagepath + product.getProductimagename();
        Path path = Paths.get(fullpath);
        Files.delete(path);
        productReposetory.delete(product);
    }

    @Override
    public ProductDto getsingleproduct(String productid) {
        Product product = productReposetory.findById(productid).orElseThrow(() -> new Resourcenotfoundexception("product not avaiable"));
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponce<ProductDto> getall(int pagenumber, int pagesize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pagenumber,pagesize,sort);
        Page<Product> page = productReposetory.findAll(pageable);
        return Healper.getPeableResponce(page,ProductDto.class);
    }



    @Override
    public PageableResponce<ProductDto> getAllive(int pagenumber, int pagesize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pagenumber,pagesize,sort);
        Page<Product> page = productReposetory.findByliveTrue(pageable);

        return Healper.getPeableResponce(page,ProductDto.class);


    }

    @Override
    public PageableResponce<ProductDto> searchbytitle(String subtitle, int pagenumber, int pagesize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pagenumber,pagesize,sort);
        Page<Product> page = productReposetory.findBytitleContaining(subtitle, pageable);
        PageableResponce<ProductDto> peableResponce = Healper.getPeableResponce(page, ProductDto.class);
        return peableResponce;
    }


    // create with category

    @Override
    public ProductDto createwithCategory(ProductDto productDto, String categoryid) {
        Category category = categoryReposetory.findById(categoryid).orElseThrow(() -> new Resourcenotfoundexception("Category not found"));
        Product product = mapper.map(productDto, Product.class);
        // rpoduct id
        String productid = UUID.randomUUID().toString();
        product.setProductid(productid);
        product.setAddeddate(new Date());
        product.setCategory(category);
        Product save = productReposetory.save(product);
        ProductDto map = mapper.map(save, ProductDto.class);
        return map;

    }

    @Override
    public ProductDto updateCategory(String productid, String categoryid) {
        // fetch product id
        Product product = productReposetory.findById(productid).orElseThrow(() -> new Resourcenotfoundexception("product of givrn id not found"));
        Category category = categoryReposetory.findById(categoryid).orElseThrow(() -> new Resourcenotfoundexception("category of this id not availible"));

        product.setCategory(category);
        Product save = productReposetory.save(product);

        return mapper.map(save,ProductDto.class);
    }

    @Override
    public PageableResponce<ProductDto> gettingalldatafromsamecateogry(String categoryid, int pagenumber, int pagesize, String sortBy, String sortDir) {
        Category category = categoryReposetory.findById(categoryid).orElseThrow(() -> new Resourcenotfoundexception("category of this id not availible"));
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable =PageRequest.of(pagenumber,pagesize,sort);
        Page<Product> bycategory = productReposetory.findBycategory(category,pageable);


        return Healper.getPeableResponce(bycategory,ProductDto.class);
    }


}
