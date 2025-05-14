package com.example.demo.mapper;

import com.example.demo.models.dto.InsumoProduccionDTO;
import com.example.demo.models.entity.InsumoProduccionEmbed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InsumoProduccionMapper {
    InsumoProduccionDTO toDTO(InsumoProduccionEmbed embed);
    InsumoProduccionEmbed toEntity(InsumoProduccionDTO dto);
}
