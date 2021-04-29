package com.rrain.springdomesticservicesapp.repo;

import com.rrain.springdomesticservicesapp.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<Service, String> {
    Service findByTitle(String title);
}
