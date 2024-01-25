package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryReposetory extends JpaRepository<Category,String> {
    List<Category> findBytitleContaining(String keywords);
}
