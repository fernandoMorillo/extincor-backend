package com.example.demo.services;

import com.example.demo.models.dto.InsumoDTO;
import java.util.List;

public interface InsumoService {
    List<InsumoDTO> findAll();
    InsumoDTO findById(String id);
    InsumoDTO save(InsumoDTO dto);
    void deleteById(String id);
}
