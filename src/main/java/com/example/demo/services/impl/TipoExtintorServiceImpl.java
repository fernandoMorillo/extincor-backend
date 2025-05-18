package com.example.demo.services.impl;

import com.example.demo.models.dto.TipoExtintorDTO;
import com.example.demo.models.enums.TipoExtintor;
import com.example.demo.services.TipoExtintorService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoExtintorServiceImpl implements TipoExtintorService {
    @Override
    public List<TipoExtintorDTO> obtenerTiposExtintor() {
        return Arrays.stream(TipoExtintor.values())
                .map(tipo -> new TipoExtintorDTO(tipo.name()))
                .collect(Collectors.toList());
    }
}
