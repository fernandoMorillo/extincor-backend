package com.example.demo.controller;

import com.example.demo.models.dto.ProduccionDTO;
import com.example.demo.services.ProduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produccion")
@CrossOrigin(origins = "*")
public class ProduccionController {

    @Autowired
    private ProduccionService produccionService;

    @GetMapping
    public List<ProduccionDTO> getAll() {
        return produccionService.findAll();
    }

    @GetMapping("/{id}")
    public ProduccionDTO getById(@PathVariable String id) {
        return produccionService.findById(id);
    }

    @PostMapping
    public ProduccionDTO create(@RequestBody ProduccionDTO dto) {
        return produccionService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        produccionService.deleteById(id);
    }
}
