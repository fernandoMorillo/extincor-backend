package com.example.demo.services;

import com.example.demo.models.dto.CompraDTO;

import java.util.List;

public interface CompraService {
    List<CompraDTO> findAll();
    CompraDTO findById(String id);
    CompraDTO save(CompraDTO compraDTO);
    void deleteById(String id);
}
