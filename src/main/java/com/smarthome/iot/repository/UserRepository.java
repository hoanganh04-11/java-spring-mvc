package com.smarthome.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthome.iot.domain.User;


//CRUD: create, read, update, delete
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoidanit);
    List<User> findByEmail(String email);

    List<User> findAll();

    User findById(long id);
}
