package com.example.demo.mapper;

import com.example.demo.models.dto.ProduccionDTO;
import com.example.demo.models.entity.Produccion;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProduccionMapper {
    default Produccion toEntity(ProduccionDTO dto) {
        if (dto == null) {
            return null;
        }

        Produccion entity = new Produccion();
        entity.setId(dto.getId());
        entity.setIdSecuencial(dto.getIdSecuencial());
        entity.setCantidadProducida(dto.getCantidadProducida());
        entity.setCodigoProduccion(dto.getCodigoProduccion());
        entity.setEstado(dto.getEstado());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setProductoNombre(dto.getProductoNombre());
        entity.setOperarioId(dto.getOperarioId());
        entity.setOrdenPedidoId(dto.getOrdenPedidoId());
        entity.setDetallePedidos(dto.getDetallePedidos());
        entity.setInsumosProduccion(dto.getInsumosProduccion());
        return entity;
    }

    default ProduccionDTO toDTO(Produccion entity) {
        if (entity == null) {
            return null;
        }

        ProduccionDTO dto = new ProduccionDTO();
        dto.setId(entity.getId());
        dto.setIdSecuencial(entity.getIdSecuencial());
        dto.setCantidadProducida(entity.getCantidadProducida());
        dto.setCodigoProduccion(entity.getCodigoProduccion());
        dto.setEstado(entity.getEstado());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setProductoNombre(entity.getProductoNombre());
        dto.setOperarioId(entity.getOperarioId());
        dto.setOrdenPedidoId(entity.getOrdenPedidoId());
        dto.setDetallePedidos(entity.getDetallePedidos());
        dto.setInsumosProduccion(entity.getInsumosProduccion());
        return dto;
    }
}
