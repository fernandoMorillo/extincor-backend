package com.example.demo.services.impl;

import com.example.demo.models.dto.ProductoDTO;
import com.example.demo.models.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.services.ProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public List<ProductoDTO> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoDTO findById(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        return producto != null ? convertToDTO(producto) : null;
    }

    @Override
    @Transactional
    public ProductoDTO save(ProductoDTO productoDTO) {
        Producto producto = convertToEntity(productoDTO);

        // Generar el código único si es un nuevo producto
        if (producto.getId() == null) {
            producto.setCodigo(generateNextCodigoProducto());
        }

        Producto savedProducto = productoRepository.save(producto);
        return convertToDTO(savedProducto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public String generateNextCodigoProducto() {
        Long lastId = productoRepository.findMaxId(); // Obtén el último ID en la base de datos
        Long nextId = (lastId != null ? lastId : 0L) + 1; // Calcula el próximo ID
        return String.format("PROD%03d", nextId); // Formatea el código como PROD001, PROD002, etc.
    }

    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setCodigo(producto.getCodigo());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setTipo(producto.getTipo());
        dto.setCapacidad(producto.getCapacidad());
        dto.setFecha_fabricacion(producto.getFecha_fabricacion());
        dto.setEstado(producto.getEstado());
        return dto;
    }

    private Producto convertToEntity(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setCodigo(productoDTO.getCodigo());
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setTipo(productoDTO.getTipo());
        producto.setCapacidad(productoDTO.getCapacidad());
        producto.setFecha_fabricacion(productoDTO.getFecha_fabricacion());
        producto.setEstado(productoDTO.getEstado());
        return producto;
    }
}
