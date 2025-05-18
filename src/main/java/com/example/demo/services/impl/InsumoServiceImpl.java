package com.example.demo.services.impl;

import com.example.demo.models.dto.InsumoDTO;
import com.example.demo.models.entity.Insumo;
import com.example.demo.models.enums.TipoExtintor;
import com.example.demo.repository.InsumoRepository;
import com.example.demo.services.InsumoService;
import com.example.demo.mapper.InsumoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsumoServiceImpl implements InsumoService {

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private InsumoMapper insumoMapper;

    @Override
    public List<InsumoDTO> findAll() {
        List<Insumo> insumos = insumoRepository.findAll();
        return insumoMapper.toDTOList(insumos);
    }

    @Override
    public InsumoDTO findById(String id) {
        return insumoRepository.findById(id)
                .map(insumoMapper::toDTO)
                .orElse(null);
    }

    @Override
    public InsumoDTO save(InsumoDTO dto) {
        Insumo insumo = insumoMapper.toEntity(dto);
        Insumo saved = insumoRepository.save(insumo);
        return insumoMapper.toDTO(saved);
    }

    @Override
    public void deleteById(String id) {
        insumoRepository.deleteById(id);
    }

    public List<Insumo> obtenerPorTipoExtintor(TipoExtintor tipo) {
        return insumoRepository.findByTiposExtintorContaining(tipo);
    }
}
