package com.example.demo.controller;

import com.example.demo.exceptions.CorreoYaExisteException;
import com.example.demo.models.dto.AdministradorDTO;
import com.example.demo.services.AdministradorService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    /**
     * Listar todos los administradores
     */
    @GetMapping
    public ResponseEntity<List<AdministradorDTO>> listAdministradores() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    /**
     * Obtener un administrador por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdministradorById(@PathVariable Long id) {
        AdministradorDTO admin = administradorService.findById(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }

    /**
     * Crear un nuevo administrador
     */
    @PostMapping
    public ResponseEntity<?> createAdministrador(@Valid @RequestBody AdministradorDTO administradorDTO) {
        try {
            administradorService.save(administradorDTO);
            return ResponseEntity.ok("Administrador creado exitosamente");
        } catch (CorreoYaExisteException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Actualizar un administrador existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdministrador(@PathVariable Long id, @Valid @RequestBody AdministradorDTO administradorDTO) {
        try {
            administradorDTO.setId(id);
            administradorService.save(administradorDTO);
            return ResponseEntity.ok("Administrador actualizado exitosamente");
        } catch (CorreoYaExisteException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Eliminar un administrador por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdministrador(@PathVariable Long id) {
        administradorService.deleteById(id);
        return ResponseEntity.ok("Administrador eliminado exitosamente");
    }
}
