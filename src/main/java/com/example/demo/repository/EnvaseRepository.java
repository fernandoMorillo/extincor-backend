package com.example.demo.repository;



import com.example.demo.models.entity.Envase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnvaseRepository extends JpaRepository<Envase, Long> {
}