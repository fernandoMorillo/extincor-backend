package com.example.demo.mapper;


import com.example.demo.models.dto.InsumoDTO;
import com.example.demo.models.entity.Insumo;
import com.example.demo.models.enums.TipoExtintor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InsumoMapper {

default InsumoDTO toDTO(Insumo insumo) {
    if (insumo == null) {
        return null;
    }
    InsumoDTO dto = new InsumoDTO();
    dto.setId(insumo.getId());
    dto.setNombre(insumo.getNombre());
    dto.setTiposExtintor(insumo.getTiposExtintor());
    dto.setCantidad(insumo.getCantidad());
    dto.setPrecioUnitario(insumo.getPrecioUnitario());
    dto.setUnidades(insumo.getUnidades());
    dto.setStock(insumo.getStock());
    return dto;
}

default Insumo toEntity(InsumoDTO dto) {
    if (dto == null) {
        return null;
    }
    Insumo entity = new Insumo();
    entity.setId(dto.getId());
    entity.setNombre(dto.getNombre());
    entity.setTiposExtintor(dto.getTiposExtintor());
    entity.setCantidad(dto.getCantidad());
    entity.setPrecioUnitario(dto.getPrecioUnitario());
    entity.setUnidades(dto.getUnidades());
    entity.setStock(dto.getStock());
    return entity;
}

}
