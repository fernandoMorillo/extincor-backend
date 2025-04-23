package com.example.demo.services.impl;

import com.example.demo.models.entity.OperarioVentana;
import com.example.demo.repository.OperarioVentanaRepository;
import com.example.demo.services.OperarioVentanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class OperarioVentanaServiceImpl implements OperarioVentanaService {

    @Autowired
    private OperarioVentanaRepository operarioVentanaRepository;

    @Override
    @Transactional
    public OperarioVentana findById(Long id) {
        return operarioVentanaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public OperarioVentana save(OperarioVentana operarioVentana) {
        return operarioVentanaRepository.save(operarioVentana);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        operarioVentanaRepository.deleteById(id);
    }
}
