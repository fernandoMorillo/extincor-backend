package com.example.demo.services.impl;

import com.example.demo.models.dto.CompraDTO;
import com.example.demo.models.entity.Compra;
import com.example.demo.models.entity.Insumo;
import com.example.demo.repository.CompraRepository;
import com.example.demo.repository.InsumoRepository;
import com.example.demo.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Override
    public List<CompraDTO> findAll() {
        return compraRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CompraDTO findById(String id) {
        return compraRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public CompraDTO save(CompraDTO dto) {
        Compra entity = toEntity(dto);

        // Obtener el insumo relacionado
        Insumo insumo = insumoRepository.findById(dto.getInsumoId())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + dto.getInsumoId()));

        // Actualizar stock
        int nuevoStock = insumo.getStock() + dto.getCantidadComprada();
        insumo.setStock(nuevoStock);
        insumoRepository.save(insumo);

        // Guardar la compra
        Compra saved = compraRepository.save(entity);

        return toDTO(saved);
    }


    @Override
    public void deleteById(String id) {
        compraRepository.deleteById(id);
    }

    private CompraDTO toDTO(Compra entity) {
        return new CompraDTO(
                entity.getId(),
                entity.getIdSecuencial(),
                entity.getDetalle(),
                entity.getEstado(),
                entity.getFechaCompra(),
                entity.getMonto(),
                entity.getProveedor(),
                entity.getCantidadComprada(),
                entity.getInsumoId(),
                entity.getDetalleCompras()
        );
    }

    private Compra toEntity(CompraDTO dto) {
        return new Compra(
                dto.getId(),
                dto.getIdSecuencial(),
                dto.getDetalle(),
                dto.getEstado(),
                dto.getFechaCompra(),
                dto.getMonto(),
                dto.getProveedor(),
                dto.getCantidadComprada(),
                dto.getInsumoId(),
                dto.getDetalleCompras()
        );
    }
}
