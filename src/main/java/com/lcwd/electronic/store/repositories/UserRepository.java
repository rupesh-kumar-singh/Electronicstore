package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

  Optional<User> findByemail(String email);
//   Optional<User> findByemailAndpassword(String email,String password);

     List<User> findBynameContaining(String keywords);


}
