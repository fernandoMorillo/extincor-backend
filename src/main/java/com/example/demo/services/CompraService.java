package com.example.demo.services;

import com.example.demo.models.dto.CompraDTO;
import java.util.List;

public interface CompraService {
    List<CompraDTO> findAll();
    CompraDTO findById(Long id);
    CompraDTO save(CompraDTO compraDTO);
    void deleteById(Long id);
}
