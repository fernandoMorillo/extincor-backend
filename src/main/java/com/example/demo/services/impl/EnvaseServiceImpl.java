package com.example.demo.services.impl;

import com.example.demo.models.dto.EnvaseDTO;
import com.example.demo.models.dto.ProductoDTO;
import com.example.demo.models.entity.Envase;
import com.example.demo.models.entity.Producto;
import com.example.demo.repository.EnvaseRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.services.EnvaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvaseServiceImpl implements EnvaseService {

    @Autowired
    private EnvaseRepository envaseRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public List<EnvaseDTO> findAll() {
        return envaseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnvaseDTO findById(Long id) {
        Envase envase = envaseRepository.findById(id).orElse(null);
        return envase != null ? convertToDTO(envase) : null;
    }

    @Override
    @Transactional
    public EnvaseDTO save(EnvaseDTO envaseDTO) {
        Envase envase = convertToEntity(envaseDTO);
        Envase savedEnvase = envaseRepository.save(envase);
        return convertToDTO(savedEnvase);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        envaseRepository.deleteById(id);
    }

    private EnvaseDTO convertToDTO(Envase envase) {
        EnvaseDTO dto = new EnvaseDTO();
        dto.setId(envase.getId());
        dto.setTipo(envase.getTipo());
        dto.setCapacidad(envase.getCapacidad());
    
        // Convertir Producto a ProductoDTO con el código
        if (envase.getProducto() != null) {
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setId(envase.getProducto().getId());
            productoDTO.setCodigo(envase.getProducto().getCodigo()); // Agregar el código del producto
            productoDTO.setNombre(envase.getProducto().getNombre()); // Agregar el nombre del producto
            dto.setProducto(productoDTO);
        }
    
        return dto;
    }
    
    private Envase convertToEntity(EnvaseDTO envaseDTO) {
        Envase envase = new Envase();
        envase.setId(envaseDTO.getId()); // Asegúrate de incluir el ID si está presente
        envase.setTipo(envaseDTO.getTipo());
        envase.setCapacidad(envaseDTO.getCapacidad());
    
        // Asociar Producto al Envase
        if (envaseDTO.getProducto() != null && envaseDTO.getProducto().getId() != null) {
            Producto producto = productoRepository.findById(envaseDTO.getProducto().getId()).orElse(null);
            envase.setProducto(producto);
        }
    
        return envase;
    }
}    
