package com.rrain.springdomesticservicesapp.repo;

import com.rrain.springdomesticservicesapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
}
