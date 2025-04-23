package com.example.demo.controller;

import com.example.demo.models.dto.EnvaseDTO;
import com.example.demo.services.EnvaseService;
import com.example.demo.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envases")
public class EnvaseController {

    @Autowired
    private EnvaseService envaseService;

    @Autowired
    private ProductoService productoService;

    // Obtener todos los envases
    @GetMapping
    public ResponseEntity<List<EnvaseDTO>> getAllEnvases() {
        return ResponseEntity.ok(envaseService.findAll());
    }

    // Obtener un envase por su ID
    @GetMapping("/{id}")
    public ResponseEntity<EnvaseDTO> getEnvaseById(@PathVariable Long id) {
        EnvaseDTO envase = envaseService.findById(id);
        if (envase == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envase);
    }

    // Crear un nuevo envase
    @PostMapping
    public ResponseEntity<EnvaseDTO> createEnvase(@RequestBody EnvaseDTO envaseDTO) {
        EnvaseDTO savedEnvase = envaseService.save(envaseDTO);
        return ResponseEntity.ok(savedEnvase);
    }

    // Actualizar un envase existente
    @PutMapping("/{id}")
    public ResponseEntity<EnvaseDTO> updateEnvase(@PathVariable Long id, @RequestBody EnvaseDTO envaseDTO) {
        EnvaseDTO existing = envaseService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        envaseDTO.setId(id); // Asegura que se actualice el correcto
        EnvaseDTO updated = envaseService.save(envaseDTO);
        return ResponseEntity.ok(updated);
    }

    // Eliminar un envase por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvase(@PathVariable Long id) {
        EnvaseDTO existing = envaseService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        envaseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
