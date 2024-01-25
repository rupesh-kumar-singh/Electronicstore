package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReposetory extends JpaRepository<Product,String> {
    Page<Product> findBytitleContaining(String subtitle,Pageable pageable);

    Page<Product> findByliveTrue(Pageable pageable);
     Page<Product> findBycategory(Category category,Pageable pageable);

}
