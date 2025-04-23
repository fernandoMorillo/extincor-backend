package com.example.demo.services.impl;

import com.example.demo.models.dto.CompraDTO;
import com.example.demo.models.entity.Compra;
import com.example.demo.repository.CompraRepository;
import com.example.demo.services.CompraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Override
    @Transactional
    public List<CompraDTO> findAll() {
        return compraRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CompraDTO findById(Long id) {
        Compra compra = compraRepository.findById(id).orElse(null);
        return compra != null ? convertToDTO(compra) : null;
    }

    @Override
    @Transactional
    public CompraDTO save(CompraDTO compraDTO) {
        Compra compra = compraDTO.getId() != null
                ? compraRepository.findById(compraDTO.getId()).orElse(new Compra())
                : new Compra();

        compra.setDetalle(compraDTO.getDetalle());
        compra.setProveedor(compraDTO.getProveedor());
        compra.setFechaCompra(compraDTO.getFechaCompra());
        compra.setEstado(compraDTO.getEstado());
        compra.setMonto(compraDTO.getMonto());
        Compra savedCompra = compraRepository.save(compra);
        return convertToDTO(savedCompra);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }

    private CompraDTO convertToDTO(Compra compra) {
        CompraDTO dto = new CompraDTO();

        dto.setId(compra.getId());
        dto.setDetalle(compra.getDetalle());
        dto.setProveedor(compra.getProveedor());
        dto.setFechaCompra(compra.getFechaCompra());
        dto.setEstado(compra.getEstado());
        dto.setMonto(compra.getMonto());
        return dto;
    }
}
