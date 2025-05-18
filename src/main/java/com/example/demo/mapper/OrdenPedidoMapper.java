package com.example.demo.mapper;

import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.models.entity.OrdenPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdenPedidoMapper {

    @Mapping(source = "tipoExtintor", target = "tipoExtintor")
    OrdenPedidoDTO toDTO(OrdenPedido orden);

    @Mapping(source = "tipoExtintor", target = "tipoExtintor")
    OrdenPedido toEntity(OrdenPedidoDTO dto);
}
