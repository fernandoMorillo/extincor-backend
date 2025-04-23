package com.example.demo.services;

import com.example.demo.models.dto.ProductoDTO;
import java.util.List;

public interface ProductoService {
    List<ProductoDTO> findAll(); // Listar todos los productos
    ProductoDTO findById(Long id); // Buscar producto por ID
    ProductoDTO save(ProductoDTO productoDTO); // Guardar o actualizar un producto
    void deleteById(Long id); // Eliminar producto por ID
    String generateNextCodigoProducto(); // Generar el próximo código único
}
