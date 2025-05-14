package com.example.demo.services.impl;

import com.example.demo.models.dto.ProduccionDTO;
import com.example.demo.models.entity.OrdenPedidoEmbed;
import com.example.demo.models.entity.Produccion;
import com.example.demo.repository.ProduccionRepository;
import com.example.demo.services.ProduccionService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduccionServiceImpl implements ProduccionService {

    @Autowired
    private ProduccionRepository produccionRepository;

    @Override
    public List<ProduccionDTO> findAll() {
        return produccionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProduccionDTO findById(String id) {

        return produccionRepository.findById(new ObjectId(id))
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public ProduccionDTO save(ProduccionDTO dto) {
        Produccion produccion = toEntity(dto);
        Produccion saved = produccionRepository.save(produccion);
        return toDTO(saved);
    }

    @Override
    public void deleteById(String id) {
        produccionRepository.deleteById(new ObjectId(id));
    }

    private ProduccionDTO toDTO(Produccion entity) {
        ProduccionDTO dto = new ProduccionDTO();

        if (entity.getId() != null) {
            dto.setId(entity.getId().toHexString());
        }

        dto.setIdSecuencial(entity.getIdSecuencial());
        dto.setCantidadProducida(entity.getCantidadProducida());
        dto.setCodigoProduccion(entity.getCodigoProduccion());
        dto.setEstado(entity.getEstado());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setProductoNombre(entity.getProductoNombre());

        if (entity.getOperarioId() != null) {
            dto.setOperarioId(entity.getOperarioId().toHexString());
        }

        if (entity.getOrdenPedidoId() != null) {
            dto.setOrdenPedidoId(entity.getOrdenPedidoId().toHexString());
        }

        dto.setOrdenesPedido((OrdenPedidoEmbed) entity.getOrdenesPedido());
        dto.setDetallePedidos(entity.getDetallePedidos());
        dto.setInsumosProduccion(entity.getInsumosProduccion());

        return dto;
    }


    private Produccion toEntity(ProduccionDTO dto) {
        Produccion entity = new Produccion();

        if (dto.getId() != null && !dto.getId().isEmpty()) {
            entity.setId(new ObjectId(dto.getId()));
        }

        entity.setIdSecuencial(dto.getIdSecuencial());
        entity.setCantidadProducida(dto.getCantidadProducida());
        entity.setCodigoProduccion(dto.getCodigoProduccion());
        entity.setEstado(dto.getEstado());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setProductoNombre(dto.getProductoNombre());

        if (dto.getOperarioId() != null && !dto.getOperarioId().isEmpty()) {
            entity.setOperarioId(new ObjectId(dto.getOperarioId()));
        }

        if (dto.getOrdenPedidoId() != null && !dto.getOrdenPedidoId().isEmpty()) {
            entity.setOrdenPedidoId(new ObjectId(dto.getOrdenPedidoId()));
        }

        entity.setOrdenesPedido((List<OrdenPedidoEmbed>) dto.getOrdenesPedido());
        entity.setDetallePedidos(dto.getDetallePedidos());
        entity.setInsumosProduccion(dto.getInsumosProduccion());

        return entity;
    }
}
