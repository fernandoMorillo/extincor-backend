package com.example.demo.repository;

import com.example.demo.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT MAX(p.id) FROM Producto p")
    Long findMaxId(); // Método para obtener el último ID
}
