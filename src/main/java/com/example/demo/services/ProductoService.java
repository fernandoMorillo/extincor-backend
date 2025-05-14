package com.example.demo.services;

import com.example.demo.models.dto.ProductoDTO;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> findAll();
    ProductoDTO findById(String id);
    ProductoDTO save(ProductoDTO productoDTO);
    void deleteById(String id);
}
