package com.example.demo.mapper;

import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.models.entity.OrdenPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdenPedidoMapper {

    default OrdenPedidoDTO toDTO(OrdenPedido orden) {
        if (orden == null) {
            return null;
        }
        OrdenPedidoDTO dto = new OrdenPedidoDTO();
        dto.setId(orden.getId());
        dto.setClienteId(orden.getClienteId());
        dto.setOperarioId(orden.getOperarioId());
        dto.setTipoExtintor(orden.getTipoExtintor());
        dto.setEstadoPedido(orden.getEstadoPedido());
        dto.setFechaPedido(orden.getFechaPedido());
        dto.setFechaEntrega(orden.getFechaEntrega());
        dto.setTipoServicio(orden.getTipoServicio());
        dto.setMontoTotal(orden.getMontoTotal());
        dto.setProduccionId(orden.getProduccionId());
        dto.setObservacion(orden.getObservacion());
        dto.setNumeroPedido(orden.getNumeroPedido());
        dto.setInsumosProduccion(orden.getInsumosProduccion());
        dto.setHoraEntrega(orden.getHoraEntrega());
        dto.setCantidad(orden.getCantidad());

        return dto;
    }

    default OrdenPedido toEntity(OrdenPedidoDTO dto) {
        if (dto == null) {
            return null;
        }
        OrdenPedido entity = new OrdenPedido();
        entity.setId(dto.getId());
        entity.setClienteId(dto.getClienteId());
        entity.setOperarioId(dto.getOperarioId());
        entity.setTipoExtintor(dto.getTipoExtintor());
        entity.setEstadoPedido(dto.getEstadoPedido());
        entity.setFechaPedido(dto.getFechaPedido());
        entity.setFechaEntrega(dto.getFechaEntrega());
        entity.setTipoServicio(dto.getTipoServicio());
        entity.setMontoTotal(dto.getMontoTotal());
        entity.setProduccionId(dto.getProduccionId());
        entity.setObservacion(dto.getObservacion());
        entity.setNumeroPedido(dto.getNumeroPedido());
        entity.setInsumosProduccion(dto.getInsumosProduccion());
        entity.setHoraEntrega(dto.getHoraEntrega());
        entity.setCantidad(dto.getCantidad());


        return entity;
    }

}
