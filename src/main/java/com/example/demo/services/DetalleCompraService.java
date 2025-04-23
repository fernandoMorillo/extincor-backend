package com.example.demo.services;

import com.example.demo.models.dto.DetalleCompraDTO;
import java.util.List;

public interface DetalleCompraService {
    List<DetalleCompraDTO> findAll();
    DetalleCompraDTO findById(Long id);
    DetalleCompraDTO save(DetalleCompraDTO detalleCompraDTO);
    void deleteById(Long id);
}
