package com.example.demo.repository;

import com.example.demo.models.entity.OrdenPedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public interface OrdenPedidoRepository extends MongoRepository<OrdenPedido, String> {

    List<OrdenPedido> findByClienteId(String clienteId);

    void deleteByClienteId(String clienteId);

    List<OrdenPedido> findByOperarioId(String operarioId);

    List<OrdenPedido> findByFechaPedido(LocalDate fechaPedido);

   // @Query("{ 'fechaEntrega' : { $gte: ?0, $lt: ?1 } }")
    long countByFechaEntrega(Date startOfDay, Date endOfDay);


}
