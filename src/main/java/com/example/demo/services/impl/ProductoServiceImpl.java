package com.example.demo.services.impl;

import com.example.demo.models.dto.ProductoDTO;
import com.example.demo.models.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductoDTO findById(String id) {
        return productoRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public ProductoDTO save(ProductoDTO dto) {
        Producto producto = toEntity(dto);
        Producto saved = productoRepository.save(producto);
        return toDTO(saved);
    }

    @Override
    public void deleteById(String id) {
        productoRepository.deleteById(id);
    }

    private ProductoDTO toDTO(Producto entity) {
        return new ProductoDTO(
                entity.getId(),
                entity.getIdSecuencial(),
                entity.getCapacidad(),
                entity.getCodigo(),
                entity.getEstado(),
                entity.getFechaFabricacion(),
                entity.getNombre(),
                entity.getPrecio(),
                entity.getTipo(),
                entity.getDetallePedidos(),
                entity.getEnvases()
        );
    }

    private Producto toEntity(ProductoDTO dto) {
        return new Producto(
                dto.getId(),
                dto.getIdSecuencial(),
                dto.getCapacidad(),
                dto.getCodigo(),
                dto.getEstado(),
                dto.getFechaFabricacion(),
                dto.getNombre(),
                dto.getPrecio(),
                dto.getTipo(),
                dto.getDetallePedidos(),
                dto.getEnvases()
        );
    }
}
