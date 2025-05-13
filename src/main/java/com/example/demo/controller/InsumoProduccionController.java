package com.example.demo.controller;

import com.example.demo.models.dto.InsumoProduccionDTO;
import com.example.demo.services.InsumoProduccionService;
import com.example.demo.services.InsumoService;
import com.example.demo.services.ProduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos-produccion")
@CrossOrigin(origins = "http://localhost:3000") // Permitir solicitudes desde React
public class InsumoProduccionController {

    @Autowired
    private InsumoProduccionService insumoProduccionService;

    @Autowired
    private InsumoService insumoService;

    @Autowired
    private ProduccionService produccionService;

    // Obtener todos los insumos de producción
    @GetMapping
    public ResponseEntity<List<InsumoProduccionDTO>> getAllInsumosProduccion() {
        return ResponseEntity.ok(insumoProduccionService.findAll());
    }

    // Guardar o actualizar un insumo de producción
    @PostMapping("/{id}/guardarinsumos")
    public ResponseEntity<InsumoProduccionDTO> saveOrUpdateInsumoProduccion(@RequestBody InsumoProduccionDTO insumoProduccionDTO) {
        InsumoProduccionDTO savedDTO = insumoProduccionService.save(insumoProduccionDTO);
        return ResponseEntity.ok(savedDTO);
    }

    // Obtener un insumo de producción por ID
    @GetMapping("/{id}")
    public ResponseEntity<InsumoProduccionDTO> getInsumoProduccionById(@PathVariable Long id) {
        return ResponseEntity.ok(insumoProduccionService.findById(id));
    }

    // Eliminar un insumo de producción por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsumoProduccion(@PathVariable Long id) {
        insumoProduccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
