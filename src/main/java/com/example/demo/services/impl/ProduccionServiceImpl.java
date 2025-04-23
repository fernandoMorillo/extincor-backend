package com.example.demo.services.impl;

import com.example.demo.models.dto.OperarioIngresoDto;
import com.example.demo.models.dto.ProduccionDTO;
import com.example.demo.models.entity.OperarioIngreso;
import com.example.demo.models.entity.Produccion;
import com.example.demo.repository.ProduccionRepository;
import com.example.demo.repository.OperarioIngresoRepository;
import com.example.demo.services.ProduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduccionServiceImpl implements ProduccionService {

    @Autowired
    private ProduccionRepository produccionRepository;

    @Autowired
    private OperarioIngresoRepository operarioIngresoRepository;

    @Override
    @Transactional
    public List<ProduccionDTO> findAll() {
        return produccionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProduccionDTO findById(Long id) {
        Produccion produccion = produccionRepository.findById(id).orElse(null);
        return produccion != null ? convertToDTO(produccion) : null;
    }

    @Override
    @Transactional
    public ProduccionDTO save(ProduccionDTO produccionDTO) {
        Produccion produccion = convertToEntity(produccionDTO);

        // Generar el código único si es una nueva producción
        if (produccion.getId() == null) {
            produccion.setCodigoProduccion(generateNextCodigoProduccion());
        }

        Produccion savedProduccion = produccionRepository.save(produccion);
        return convertToDTO(savedProduccion);
    }

    @Override
    @Transactional
    public String generateNextCodigoProduccion() {
        Long lastId = produccionRepository.findMaxId(); // Obtén el último ID
        Long nextId = (lastId != null ? lastId : 0L) + 1; // Calcula el siguiente
        return String.format("P%03d", nextId); // Devuelve el código formateado
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        produccionRepository.deleteInsumosByProduccionId(id); // Limpia la relación
        produccionRepository.deleteById(id); // Ahora elimina la producción
    }

    private ProduccionDTO convertToDTO(Produccion produccion) {
        ProduccionDTO dto = new ProduccionDTO();
        dto.setId(produccion.getId());
        dto.setCodigoProduccion(produccion.getCodigoProduccion()); // Asigna el código
        dto.setFechaInicio(produccion.getFechaInicio());
        dto.setFechaFin(produccion.getFechaFin());
        dto.setCantidad_producida(produccion.getCantidad_producida());
        dto.setProducto_nombre(produccion.getProducto_nombre());
        dto.setEstado(produccion.getEstado());

        // Convertir OperarioIngreso a OperarioIngresoDto
        if (produccion.getOperario() != null) {
            OperarioIngresoDto operarioDTO = new OperarioIngresoDto();
            operarioDTO.setId(produccion.getOperario().getId());
            operarioDTO.setNombre(produccion.getOperario().getNombre());
            operarioDTO.setDireccion(produccion.getOperario().getDireccion());
            operarioDTO.setTelefono(produccion.getOperario().getTelefono());

            dto.setOperario(operarioDTO);
        }

        return dto;
    }

    private Produccion convertToEntity(ProduccionDTO produccionDTO) {
        Produccion produccion = new Produccion();
        produccion.setId(produccionDTO.getId());
        produccion.setFechaInicio(produccionDTO.getFechaInicio());
        produccion.setFechaFin(produccionDTO.getFechaFin());
        produccion.setCantidad_producida(produccionDTO.getCantidad_producida());
        produccion.setProducto_nombre(produccionDTO.getProducto_nombre());
        produccion.setEstado(produccionDTO.getEstado());

        // Asociar OperarioIngreso si existe
        if (produccionDTO.getOperario() != null && produccionDTO.getOperario().getId() != null) {
            OperarioIngreso operario = operarioIngresoRepository.findById(produccionDTO.getOperario().getId()).orElse(null);
            produccion.setOperario(operario);
        }

        return produccion;
    }
}
