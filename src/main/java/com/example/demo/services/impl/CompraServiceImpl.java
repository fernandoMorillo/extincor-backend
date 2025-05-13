package com.example.demo.services.impl;

import com.example.demo.models.dto.CompraDTO;
import com.example.demo.models.entity.Compra;
import com.example.demo.models.entity.DetalleCompra;
import com.example.demo.models.entity.Insumo;
import com.example.demo.repository.CompraRepository;
import com.example.demo.repository.DetalleCompraRepository;
import com.example.demo.repository.InsumoRepository;
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

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

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
        compra.setFecha_compra(compraDTO.getFecha_compra());
        compra.setEstado(compraDTO.getEstado());
        compra.setMonto(compraDTO.getMonto());

        Insumo insumo = insumoRepository.findById(compraDTO.getInsumo_id())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));
        insumo.setStock(insumo.getStock() + compraDTO.getCantidadComprada());
        insumoRepository.save(insumo);


        Compra savedCompra = compraRepository.save(compra);

        DetalleCompra detalleCompra = new DetalleCompra();
        detalleCompra.setCantidad(compraDTO.getCantidadComprada());
        detalleCompra.setInsumo(insumo);
        detalleCompra.setCompra(savedCompra);

        detalleCompraRepository.save(detalleCompra);

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
        dto.setFecha_compra(compra.getFecha_compra());
        dto.setEstado(compra.getEstado());
        dto.setMonto(compra.getMonto());
        dto.setCantidadComprada(compra.getCantidadComprada());

        return dto;
    }
}
