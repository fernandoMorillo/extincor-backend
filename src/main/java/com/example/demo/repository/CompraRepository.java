package com.example.demo.repository;

import com.example.demo.models.entity.Compra;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompraRepository extends MongoRepository<Compra, String> {
}
