package com.example.demo.mapper;

import com.example.demo.models.dto.DetallePedidoDTO;
import com.example.demo.models.entity.DetallePedidoEmbed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {
    DetallePedidoDTO toDTO(DetallePedidoEmbed embed);
    DetallePedidoEmbed toEntity(DetallePedidoDTO dto);
}
