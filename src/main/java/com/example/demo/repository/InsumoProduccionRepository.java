package com.example.demo.repository;

import com.example.demo.models.entity.InsumoProduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoProduccionRepository extends JpaRepository<InsumoProduccion, Long> {
}