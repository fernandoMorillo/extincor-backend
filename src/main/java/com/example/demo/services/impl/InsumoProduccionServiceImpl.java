package com.example.demo.services.impl;

import com.example.demo.models.dto.InsumoProduccionDTO;
import com.example.demo.models.dto.InsumoDTO;
import com.example.demo.models.dto.ProduccionDTO;
import com.example.demo.models.entity.Insumo;
import com.example.demo.models.entity.InsumoProduccion;
import com.example.demo.models.entity.Produccion;
import com.example.demo.repository.InsumoProduccionRepository;
import com.example.demo.repository.InsumoRepository;
import com.example.demo.repository.ProduccionRepository;
import com.example.demo.services.InsumoProduccionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsumoProduccionServiceImpl implements InsumoProduccionService {

    @Autowired
    private InsumoProduccionRepository insumoProduccionRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private ProduccionRepository produccionRepository;

    @Override
    @Transactional
    public List<InsumoProduccionDTO> findAll() {
        return insumoProduccionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InsumoProduccionDTO findById(Long id) {
        InsumoProduccion insumoProduccion = insumoProduccionRepository.findById(id).orElse(null);
        return insumoProduccion != null ? convertToDTO(insumoProduccion) : null;
    }

    @Override
    @Transactional
    public InsumoProduccionDTO save(InsumoProduccionDTO insumoProduccionDTO) {
        // Convertir DTO a entidad
        InsumoProduccion insumoProduccion = convertToEntity(insumoProduccionDTO);
    
        // Validar que el insumo exista
        Insumo insumo = insumoProduccion.getInsumo();
        if (insumo == null || insumo.getId() == null) {
            throw new RuntimeException("El insumo asociado no existe.");
        }
    
        // Validar stock suficiente
        if (insumo.getStock() < insumoProduccion.getCantidad()) {
            throw new RuntimeException("No hay suficiente stock para este insumo.");
        }
    
        // Descontar la cantidad del stock
        insumo.setStock(insumo.getStock() - insumoProduccion.getCantidad());
        insumoRepository.save(insumo);
    
        // Guardar InsumoProduccion
        InsumoProduccion savedInsumoProduccion = insumoProduccionRepository.save(insumoProduccion);
    
        return convertToDTO(savedInsumoProduccion);
    }
    

    @Override
    @Transactional
    public void deleteById(Long id) {
        insumoProduccionRepository.deleteById(id);
    }

    private InsumoProduccionDTO convertToDTO(InsumoProduccion insumoProduccion) {
        InsumoProduccionDTO dto = new InsumoProduccionDTO();
        dto.setId(insumoProduccion.getId());
        dto.setCantidad(insumoProduccion.getCantidad());

        // Convertir Insumo a InsumoDTO
        if (insumoProduccion.getInsumo() != null) {
            InsumoDTO insumoDTO = new InsumoDTO();
            insumoDTO.setId(insumoProduccion.getInsumo().getId());
            insumoDTO.setNombre(insumoProduccion.getInsumo().getNombre());
            dto.setInsumo(insumoDTO);
        }

        // Convertir Produccion a ProduccionDTO
        if (insumoProduccion.getProduccion() != null) {
            ProduccionDTO produccionDTO = new ProduccionDTO();
            produccionDTO.setId(insumoProduccion.getProduccion().getId());
            dto.setProduccion(produccionDTO);
        }

        return dto;
    }

    private InsumoProduccion convertToEntity(InsumoProduccionDTO insumoProduccionDTO) {
        InsumoProduccion insumoProduccion = new InsumoProduccion();
        insumoProduccion.setCantidad(insumoProduccionDTO.getCantidad());

        // Asociar Insumo si existe
        if (insumoProduccionDTO.getInsumo() != null && insumoProduccionDTO.getInsumo().getId() != null) {
            Insumo insumo = insumoRepository.findById(insumoProduccionDTO.getInsumo().getId()).orElse(null);
            insumoProduccion.setInsumo(insumo);
        }

        // Asociar Produccion si existe
        if (insumoProduccionDTO.getProduccion() != null && insumoProduccionDTO.getProduccion().getId() != null) {
            Produccion produccion = produccionRepository.findById(insumoProduccionDTO.getProduccion().getId()).orElse(null);
            insumoProduccion.setProduccion(produccion);
        }

        return insumoProduccion;
    }
}
