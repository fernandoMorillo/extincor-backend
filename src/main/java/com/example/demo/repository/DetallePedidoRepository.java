package com.example.demo.repository;

import com.example.demo.models.entity.DetallePedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends MongoRepository<DetallePedido, String> {

    void deleteByOrdenPedidoId(String ordenPedidoId);
}
