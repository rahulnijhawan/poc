package com.rahul.springsecurity.repositories;

import com.rahul.springsecurity.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepositoryy extends CrudRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
