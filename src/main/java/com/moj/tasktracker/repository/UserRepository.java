package com.moj.tasktracker.repository;

import com.moj.tasktracker.api.model.User;
import java.util.Optional;
import org.springframework.data.repository.Repository;


public interface UserRepository extends Repository<User, Integer> {

  User save(User entity);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
