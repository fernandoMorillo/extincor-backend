package com.example.demo.controller;

import com.example.demo.models.dto.CompraDTO;
import com.example.demo.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public List<CompraDTO> getAll() {
        return compraService.findAll();
    }

    @GetMapping("/{id}")
    public CompraDTO getById(@PathVariable String id) {
        return compraService.findById(id);
    }

    @PostMapping
    public CompraDTO create(@RequestBody CompraDTO dto) {
        return compraService.save(dto);
    }

    @PutMapping("/{id}")
    public CompraDTO update(@PathVariable String id, @RequestBody CompraDTO dto) {
        dto.setId(id);
        return compraService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        compraService.deleteById(id);
    }
}
