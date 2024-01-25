package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CartitemReposetroy extends JpaRepository<Cartitem,Integer> {
}
