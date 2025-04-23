package com.example.demo.services;

import com.example.demo.models.dto.InsumoProduccionDTO;
import java.util.List;

public interface InsumoProduccionService {
    List<InsumoProduccionDTO> findAll();
    InsumoProduccionDTO findById(Long id);
    InsumoProduccionDTO save(InsumoProduccionDTO insumoProduccionDTO);
    void deleteById(Long id);
}
