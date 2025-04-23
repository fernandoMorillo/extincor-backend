package com.example.demo.repository;

import com.example.demo.models.entity.OperarioVentana;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperarioVentanaRepository extends JpaRepository<OperarioVentana, Long> {
    // Puedes añadir métodos específicos si los necesitas
}
