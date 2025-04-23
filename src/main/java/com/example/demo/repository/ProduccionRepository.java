package com.example.demo.repository;

import com.example.demo.models.entity.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion, Long> {
    @Modifying
    @Query("DELETE FROM InsumoProduccion ip WHERE ip.produccion.id = :produccionId")
    void deleteInsumosByProduccionId(@Param("produccionId") Long produccionId);
    @Query("SELECT MAX(p.id) FROM Produccion p")
    Long findMaxId();
}