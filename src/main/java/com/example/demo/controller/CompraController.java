package com.example.demo.controller;

import com.example.demo.models.dto.CompraDTO;
import com.example.demo.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    /**
     * Listar todas las compras
     */
    @GetMapping
    public ResponseEntity<List<CompraDTO>> listCompras() {
        return ResponseEntity.ok(compraService.findAll());
    }

    /**
     * Obtener compra por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompraById(@PathVariable Long id) {
        CompraDTO compra = compraService.findById(id);
        if (compra == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(compra);
    }

    /**
     * Crear una nueva compra
     */
    @PostMapping
    public ResponseEntity<?> createCompra(@RequestBody CompraDTO compraDTO) {
        compraService.save(compraDTO);
        return ResponseEntity.ok("Compra creada exitosamente");
    }

    /**
     * Actualizar una compra existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompra(@PathVariable Long id, @RequestBody CompraDTO compraDTO) {
        compraDTO.setId(id);
        compraService.save(compraDTO);
        return ResponseEntity.ok("Compra actualizada exitosamente");
    }

    /**
     * Eliminar una compra por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompra(@PathVariable Long id) {
        compraService.deleteById(id);
        return ResponseEntity.ok("Compra eliminada exitosamente");
    }
}
