package com.example.demo.services.impl;

import com.example.demo.models.dto.InsumoDTO;
import com.example.demo.models.entity.Insumo;
import com.example.demo.models.enums.TipoExtintor;
import com.example.demo.repository.InsumoRepository;
import com.example.demo.services.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsumoServiceImpl implements InsumoService {

    @Autowired
    private InsumoRepository insumoRepository;

    @Override
    public List<InsumoDTO> findAll() {
        return insumoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InsumoDTO findById(String id) {
        return insumoRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public InsumoDTO save(InsumoDTO dto) {
        Insumo insumo = toEntity(dto);
        Insumo saved = insumoRepository.save(insumo);
        return toDTO(saved);
    }

    @Override
    public void deleteById(String id) {
        insumoRepository.deleteById(id);
    }

    public List<Insumo> obtenerPorTipoExtintor(TipoExtintor tipo) {
        return insumoRepository.findByTiposExtintorContaining(tipo);
    }

    private InsumoDTO toDTO(Insumo entity) {
        InsumoDTO dto = new InsumoDTO();
        dto.setId(entity.getId());
        dto.setIdSecuencial(entity.getIdSecuencial());
        dto.setCantidad(entity.getCantidad());
        dto.setFechaIngreso(entity.getFechaIngreso());
        dto.setNombre(entity.getNombre());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setStock(entity.getStock());
        dto.setStockMinimo(entity.getStockMinimo());
        dto.setUnidades(entity.getUnidades());
        dto.setInsumosProduccion(entity.getInsumosProduccion());
        dto.setOrdenesPedido(entity.getOrdenesPedido());
        dto.setDetalleCompras(entity.getDetalleCompras());
        return dto;
    }

    private Insumo toEntity(InsumoDTO dto) {
        Insumo entity = new Insumo();
        entity.setId(dto.getId());
        entity.setIdSecuencial(dto.getIdSecuencial());
        entity.setCantidad(dto.getCantidad());
        entity.setFechaIngreso(dto.getFechaIngreso());
        entity.setNombre(dto.getNombre());
        entity.setPrecioUnitario(dto.getPrecioUnitario());
        entity.setStock(dto.getStock());
        entity.setStockMinimo(dto.getStockMinimo());
        entity.setUnidades(dto.getUnidades());
        entity.setInsumosProduccion(dto.getInsumosProduccion());
        entity.setOrdenesPedido(dto.getOrdenesPedido());
        entity.setDetalleCompras(dto.getDetalleCompras());
        return entity;
    }
}
