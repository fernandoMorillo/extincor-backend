package com.example.demo.services;

import com.example.demo.models.dto.InsumoDTO;
import java.util.List;

public interface InsumoService {
    List<InsumoDTO> findAll();
    InsumoDTO findById(Long id);
    InsumoDTO save(InsumoDTO insumoDTO);
    void deleteById(Long id);
}
