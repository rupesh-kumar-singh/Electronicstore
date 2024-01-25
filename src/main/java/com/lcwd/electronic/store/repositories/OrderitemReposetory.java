package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderitemReposetory extends JpaRepository<Orderitem,Integer> {

}
