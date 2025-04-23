package com.example.demo.repository;


import com.example.demo.models.entity.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

}