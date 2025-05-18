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

    @Mapping(source = "tiposExtintor", target = "tiposExtintor")
    InsumoDTO toDTO(Insumo insumo);

    @Mapping(source = "tiposExtintor", target = "tiposExtintor")
    List<InsumoDTO> toDTOList(List<Insumo> insumos);

    // DTO a Entidad
    @Mapping(source = "tiposExtintor", target = "tiposExtintor")
    Insumo toEntity(InsumoDTO dto);

    @Mapping(source = "tiposExtintor", target = "tiposExtintor")
    List<Insumo> toEntityList(List<InsumoDTO> dtos);

    // Métodos de conversión personalizados

    @Named("enumListToStringList")
    static List<String> enumListToStringList(List<TipoExtintor> enums) {
        return enums == null ? null : enums.stream()
                .map(TipoExtintor::name)
                .collect(Collectors.toList());
    }

    @Named("stringListToEnumList")
    static List<TipoExtintor> stringListToEnumList(List<String> strings) {
        return strings == null ? null : strings.stream()
                .map(s -> TipoExtintor.valueOf(s.toUpperCase()))
                .collect(Collectors.toList());
    }
}
