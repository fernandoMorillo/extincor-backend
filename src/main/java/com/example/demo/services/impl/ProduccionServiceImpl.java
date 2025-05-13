package com.example.demo.services.impl;

import com.example.demo.models.dto.OperarioIngresoDto;
import com.example.demo.models.dto.ProduccionDTO;
import com.example.demo.models.entity.*;
import com.example.demo.repository.*;
import com.example.demo.services.ProduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduccionServiceImpl implements ProduccionService {

    @Autowired
    private ProduccionRepository produccionRepository;

    @Autowired
    private OperarioIngresoRepository operarioIngresoRepository;

    @Autowired
    private InsumoProduccionRepository insumoProduccionRepository;

    @Autowired
    private InsumoRepository insumoRepository;
    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;

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

        if (produccion.getId() == null) {
            produccion.setCodigoProduccion(generateNextCodigoProduccion());
            produccion.setFechaInicio(LocalDateTime.now());
            produccion.setEstado("EN_PROCESO");
        }

        Produccion savedProduccion = produccionRepository.save(produccion);

        // Asociar insumos a la producción
        if (produccionDTO.getInsumosProduccion() != null) {
            for (Long insumoId : produccionDTO.getInsumosProduccion()) {
                Insumo insumo = insumoRepository.findById(insumoId)
                        .orElseThrow(() -> new RuntimeException("Insumo no encontrado: ID " + insumoId));

               InsumoProduccion ip = new InsumoProduccion();
                ip.setInsumo(insumo);
                ip.setProduccion(savedProduccion);
                ip.setEstado("EN_PROCESO");

                insumoProduccionRepository.save(ip);
            }
        }

        return convertToDTO(savedProduccion);
    }

    @Override
    @Transactional
    public ProduccionDTO iniciarProduccion(Long ordenId) {
        OrdenPedido orden = ordenPedidoRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (orden.getProduccion() != null) {
            throw new RuntimeException("La producción ya fue iniciada para esta orden.");
        }

        Produccion produccion = new Produccion();
        produccion.setOrdenPedido(orden);
        produccion.setFechaInicio(LocalDateTime.now());
        produccion.setEstado("EN_PROCESO");
        produccion.setCodigoProduccion("PROD" + orden.getNumeroPedido()); // Puedes ajustar el formato

        Produccion guardada = produccionRepository.save(produccion);
        orden.setProduccion(guardada); // Para mantener la relación bidireccional

        ProduccionDTO dto = new ProduccionDTO();
        dto.setId(guardada.getId());
        dto.setEstado(guardada.getEstado());
        dto.setFechaInicio(guardada.getFechaInicio());
        dto.setCodigoProduccion(guardada.getCodigoProduccion());

        return dto;
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
    public void marcarProduccionComoFinalizada(Long produccionId) {
        Produccion produccion = produccionRepository.findById(produccionId)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));

        produccion.setEstado("FINALIZADO");
        produccion.setFechaFin(LocalDateTime.now());
        produccionRepository.save(produccion);

        // Actualizar estado de los insumos asociados
        List<InsumoProduccion> insumos = insumoProduccionRepository.findByProduccionId(produccionId);
        for (InsumoProduccion insumo : insumos) {
            insumo.setEstado("FINALIZADO");
        }
        insumoProduccionRepository.saveAll(insumos);
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
            OperarioIngreso operarioDTO = new OperarioIngreso();
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
            OperarioIngreso operario = operarioIngresoRepository.findById(produccionDTO.getOperario().getId()).orElseThrow(() -> new RuntimeException("Operario no encontrado"));
            produccion.setOperario(operario);
        }

        return produccion;
    }
}
