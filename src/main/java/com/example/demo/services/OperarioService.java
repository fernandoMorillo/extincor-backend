package com.example.demo.services;

import com.example.demo.models.dto.OperarioIngresoDto;

import java.util.List;

public interface OperarioService {
    List<OperarioIngresoDto> findAll();
    OperarioIngresoDto findById(Long id);
    OperarioIngresoDto save(OperarioIngresoDto operarioDTO);
    void deleteById(Long id);

    // Nuevo método para obtener el ID por correo
    Long findIdByCorreo(String correo);
}
