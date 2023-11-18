package com.example.nezok.repositories;

import com.example.nezok.models.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserModel, Integer> {

    Optional<UserModel> findByEmail(String email);
}
