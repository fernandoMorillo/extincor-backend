package com.example.demo.services;

import com.example.demo.models.dto.ProduccionDTO;
import java.util.List;

public interface ProduccionService {
    List<ProduccionDTO> findAll();
    ProduccionDTO findById(Long id);
    ProduccionDTO save(ProduccionDTO produccionDTO);
    void deleteById(Long id);

    /**
     * Genera el próximo código de producción en el formato P001, P002, etc.
     * @return El próximo código de producción.
     */
    String generateNextCodigoProduccion();
}
