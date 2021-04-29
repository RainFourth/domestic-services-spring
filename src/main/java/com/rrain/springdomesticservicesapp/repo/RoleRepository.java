package com.rrain.springdomesticservicesapp.repo;

import com.rrain.springdomesticservicesapp.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByRole(String role);
}
