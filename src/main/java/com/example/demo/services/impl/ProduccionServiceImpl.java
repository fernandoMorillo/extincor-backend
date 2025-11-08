package com.example.demo.services.impl;

import com.example.demo.mapper.ProduccionMapper;
import com.example.demo.models.dto.ProduccionDTO;

import com.example.demo.models.entity.Produccion;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.repository.ProduccionRepository;
import com.example.demo.services.ProduccionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduccionServiceImpl implements ProduccionService {

    @Autowired
    private ProduccionRepository produccionRepository;

    @Autowired
    private ProduccionMapper produccionMapper;
    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;

    @Override
    public List<ProduccionDTO> findAll() {
        return produccionRepository.findAll()
                .stream()
                .map(produccionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProduccionDTO findById(String id) {
        return produccionRepository.findById(id)
                .map(produccionMapper::toDTO)
                .orElse(null);
    }

    @Override
    public ProduccionDTO save(ProduccionDTO dto) {
        Produccion produccion = produccionMapper.toEntity(dto);
        System.out.println("produccion que llega 2: " + produccion);

        Produccion saved = produccionRepository.save(produccion);
        String produccionId = saved.getId(); // Asegúrate de usar el ID del objeto guardado

        ordenPedidoRepository.findById(saved.getOrdenPedidoId()).ifPresent(orden -> {
            // Agregar el ID de la producción a la orden de pedido
            orden.setProduccionId(produccionId); // ← Asegúrate de tener este campo en la entidad OrdenPedido
            ordenPedidoRepository.save(orden);
        });

        return produccionMapper.toDTO(saved);
    }

    @Override
    public void deleteById(String id) {
        produccionRepository.deleteById(id);
    }
}
