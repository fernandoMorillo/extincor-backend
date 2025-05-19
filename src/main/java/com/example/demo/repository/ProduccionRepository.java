package com.example.demo.repository;

import com.example.demo.models.entity.Produccion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProduccionRepository extends MongoRepository<Produccion, String> {}
