package com.example.demo.services;

import com.example.demo.models.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> findAll();
    UsuarioDTO findById(String id);
    UsuarioDTO save(UsuarioDTO dto);
    void deleteById(String id);
}
