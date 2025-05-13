package com.example.demo.controller;

import com.example.demo.models.dto.ProduccionDTO;
import com.example.demo.services.ProduccionService;
import com.example.demo.services.impl.ProduccionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producciones/")
public class ProduccionController {

    @Autowired
    private ProduccionService produccionService;
    @Autowired
    private ProduccionServiceImpl produccionServiceImpl;

    // Obtener todas las producciones
    @GetMapping
    public ResponseEntity<List<ProduccionDTO>> getAllProducciones() {
        List<ProduccionDTO> producciones = produccionService.findAll();
        return ResponseEntity.ok(producciones);
    }

    // Obtener una producción por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProduccionDTO> getProduccionById(@PathVariable Long id) {
        ProduccionDTO produccion = produccionService.findById(id);
        if (produccion != null) {
            return ResponseEntity.ok(produccion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear o actualizar producción
    @PostMapping("/iniciar")
    public ResponseEntity<ProduccionDTO> saveProduccion(@RequestBody ProduccionDTO produccionDTO) {
        ProduccionDTO saved = produccionService.save(produccionDTO);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{id}/iniciar-produccion")
    public ResponseEntity<ProduccionDTO> iniciarProduccion(@PathVariable Long id) {
        ProduccionDTO produccion = produccionServiceImpl.iniciarProduccion(id);
        return ResponseEntity.ok(produccion);
    }


    // Eliminar producción por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduccion(@PathVariable Long id) {
        produccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Finalizar producción (nuevo endpoint)
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<String> finalizarProduccion(@PathVariable Long id) {
        try {
            produccionService.marcarProduccionComoFinalizada(id);
            return ResponseEntity.ok("Producción marcada como finalizada.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al finalizar la producción: " + e.getMessage());
        }
    }
}
