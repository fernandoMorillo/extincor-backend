package com.example.demo.controller;

import com.example.demo.models.dto.ProductoDTO;
import com.example.demo.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> getAll() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ProductoDTO getById(@PathVariable String id) {
        return productoService.findById(id);
    }

    @PostMapping
    public ProductoDTO create(@RequestBody ProductoDTO dto) {
        return productoService.save(dto);
    }

    @PutMapping("/{id}")
    public ProductoDTO update(@PathVariable String id, @RequestBody ProductoDTO dto) {
        dto.setId(id);
        return productoService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        productoService.deleteById(id);
    }
}
