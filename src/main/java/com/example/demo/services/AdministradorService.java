package com.example.demo.services;

import com.example.demo.models.dto.AdministradorDTO;
import java.util.List;

public interface AdministradorService {
    List<AdministradorDTO> findAll();
    AdministradorDTO findById(Long id);
    AdministradorDTO save(AdministradorDTO administradorDTO);
    void deleteById(Long id);
}
