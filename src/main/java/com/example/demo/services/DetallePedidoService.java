package com.example.demo.services;

import com.example.demo.models.dto.DetallePedidoDTO;
import java.util.List;

public interface DetallePedidoService {
    List<DetallePedidoDTO> findAll();
    DetallePedidoDTO findById(Long id);
    DetallePedidoDTO save(DetallePedidoDTO detallePedidoDTO);
    void deleteById(Long id);
}
