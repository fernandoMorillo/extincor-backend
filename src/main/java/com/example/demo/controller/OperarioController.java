package com.example.demo.controller;

import com.example.demo.exceptions.CorreoYaExisteException;
import com.example.demo.models.dto.OperarioIngresoDto;
import com.example.demo.services.OperarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operarios")
public class OperarioController {

    @Autowired
    private OperarioService operarioService;

    // Obtener todos los operarios
    @GetMapping
    public ResponseEntity<List<OperarioIngresoDto>> getAllOperarios() {
        return ResponseEntity.ok(operarioService.findAll());
    }

    // Obtener un operario por ID
    @GetMapping("/{id}")
    public ResponseEntity<OperarioIngresoDto> getOperarioById(@PathVariable Long id) {
        OperarioIngresoDto operario = operarioService.findById(id);
        if (operario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(operario);
    }

    // Crear un nuevo operario
    @PostMapping
    public ResponseEntity<?> createOperario(@RequestBody OperarioIngresoDto operarioDTO) {
        try {
            OperarioIngresoDto saved = operarioService.save(operarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (CorreoYaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar un operario existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOperario(@PathVariable Long id, @RequestBody OperarioIngresoDto operarioDTO) {
        OperarioIngresoDto existing = operarioService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            operarioDTO.setId(id); // Asegura que se actualiza el correcto
            OperarioIngresoDto updated = operarioService.save(operarioDTO);
            return ResponseEntity.ok(updated);
        } catch (CorreoYaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Eliminar un operario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperario(@PathVariable Long id) {
        OperarioIngresoDto existing = operarioService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        operarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener la "ventana" de un operario (vista previa)
    @GetMapping("/ventana/{id}")
    public ResponseEntity<?> getVentanaOperario(@PathVariable Long id) {
        OperarioIngresoDto operario = operarioService.findById(id);
        if (operario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operario no encontrado");
        }
        return ResponseEntity.ok(operario);
    }
}
