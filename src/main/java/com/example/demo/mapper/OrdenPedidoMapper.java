package com.example.demo.mapper;

import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.models.entity.OrdenPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DetallePedidoMapper.class, InsumoProduccionMapper.class})
public interface OrdenPedidoMapper {

    OrdenPedidoDTO toDTO(OrdenPedido orden);
    OrdenPedido toEntity(OrdenPedidoDTO dto);
}
