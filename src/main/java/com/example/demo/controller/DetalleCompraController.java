package com.example.demo.controller;

import com.example.demo.models.dto.DetalleCompraDTO;
import com.example.demo.services.CompraService;
import com.example.demo.services.DetalleCompraService;
import com.example.demo.services.InsumoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-compra")
public class DetalleCompraController {

    @Autowired
    private DetalleCompraService detalleCompraService;

    @Autowired
    private CompraService compraService;

    @Autowired
    private InsumoService insumoService;

    /**
     * Obtener todos los detalles de compra
     */
    @GetMapping
    public ResponseEntity<List<DetalleCompraDTO>> listDetalles() {
        return ResponseEntity.ok(detalleCompraService.findAll());
    }

    /**
     * Obtener un detalle de compra por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDetalleById(@PathVariable Long id) {
        DetalleCompraDTO detalle = detalleCompraService.findById(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalle);
    }

    /**
     * Crear un nuevo detalle de compra
     */
    @PostMapping
    public ResponseEntity<?> createDetalle(@RequestBody DetalleCompraDTO detalleCompraDTO) {
        detalleCompraService.save(detalleCompraDTO);
        return ResponseEntity.ok("Detalle de compra creado exitosamente");
    }

    /**
     * Actualizar un detalle de compra existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDetalle(@PathVariable Long id, @RequestBody DetalleCompraDTO detalleCompraDTO) {
        detalleCompraDTO.setId(id);
        detalleCompraService.save(detalleCompraDTO);
        return ResponseEntity.ok("Detalle de compra actualizado exitosamente");
    }

    /**
     * Eliminar un detalle de compra por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDetalle(@PathVariable Long id) {
        detalleCompraService.deleteById(id);
        return ResponseEntity.ok("Detalle de compra eliminado exitosamente");
    }

    /**
     * Obtener insumos disponibles (opcional para frontend)
     */
    @GetMapping("/insumos")
    public ResponseEntity<?> getInsumos() {
        return ResponseEntity.ok(insumoService.findAll());
    }

    /**
     * Obtener compras disponibles (opcional para frontend)
     */
    @GetMapping("/compras")
    public ResponseEntity<?> getCompras() {
        return ResponseEntity.ok(compraService.findAll());
    }
}
