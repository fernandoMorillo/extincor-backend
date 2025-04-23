package com.example.demo.repository;


import com.example.demo.models.entity.OperarioIngreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface  OperarioIngresoRepository extends JpaRepository<OperarioIngreso, Long> {
    Optional<OperarioIngreso> findByCorreo(String correo);
}