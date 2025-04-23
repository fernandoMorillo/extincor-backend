package com.example.demo.services;

import com.example.demo.models.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> findAll();
    UsuarioDTO findById(Long id);
    UsuarioDTO save(UsuarioDTO usuarioDTO);
    void deleteById(Long id);
}
