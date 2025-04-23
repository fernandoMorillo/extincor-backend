package com.example.demo.controller;

import com.example.demo.models.dto.InsumoDTO;
import com.example.demo.services.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@CrossOrigin(origins = "http://localhost:3000")
public class InsumoController {

    @Autowired
    private InsumoService insumoService;

    // Obtener todos los insumos
    @GetMapping
    public ResponseEntity<List<InsumoDTO>> listInsumos() {
        List<InsumoDTO> insumos = insumoService.findAll();
        return ResponseEntity.ok(insumos);
    }

    // Guardar o actualizar un insumo
    @PostMapping
    public ResponseEntity<InsumoDTO> saveOrUpdateInsumo(@RequestBody InsumoDTO insumoDTO) {
        InsumoDTO savedInsumo = insumoService.save(insumoDTO);
        return ResponseEntity.ok(savedInsumo);
    }

    // Obtener un insumo por ID
    @GetMapping("/{id}")
    public ResponseEntity<InsumoDTO> getInsumoById(@PathVariable Long id) {
        InsumoDTO insumo = insumoService.findById(id);
        return insumo != null ? ResponseEntity.ok(insumo) : ResponseEntity.notFound().build();
    }

    // Eliminar un insumo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsumo(@PathVariable Long id) {
        insumoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
