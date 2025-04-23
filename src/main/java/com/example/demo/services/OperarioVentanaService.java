package com.example.demo.services;

import com.example.demo.models.entity.OperarioVentana;

public interface OperarioVentanaService {
    OperarioVentana findById(Long id);
    OperarioVentana save(OperarioVentana operarioVentana);
    void deleteById(Long id);
}
