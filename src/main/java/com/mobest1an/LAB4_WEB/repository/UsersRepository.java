package com.mobest1an.LAB4_WEB.repository;

import com.mobest1an.LAB4_WEB.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
