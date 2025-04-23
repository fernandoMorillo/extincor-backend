package com.example.demo.repository;



import com.example.demo.models.entity.DetallePedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM DetallePedido dp WHERE dp.ordenpedido.id = :ordenPedidoId")
    void deleteByOrdenPedidoId(@Param("ordenPedidoId") Long ordenPedidoId);
}