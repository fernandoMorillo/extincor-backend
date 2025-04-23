package com.example.demo.services;

import com.example.demo.models.dto.EnvaseDTO;
import java.util.List;

public interface EnvaseService {
    List<EnvaseDTO> findAll();
    EnvaseDTO findById(Long id);
    EnvaseDTO save(EnvaseDTO envaseDTO);
    void deleteById(Long id);
}
