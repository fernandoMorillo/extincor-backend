package com.example.demo.services;

import com.example.demo.models.dto.ProduccionDTO;
import java.util.List;

public interface ProduccionService {
    List<ProduccionDTO> findAll();
    ProduccionDTO findById(String id);
    ProduccionDTO save(ProduccionDTO dto);
    void deleteById(String id);
}
