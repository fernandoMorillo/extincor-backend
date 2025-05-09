package com.example.demo.controller;

import com.example.demo.models.dto.EstadoPedidoRequestDTO;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.services.OrdenPedidoService;
import com.example.demo.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenPedidoController {

    @Autowired
    private OrdenPedidoService ordenPedidoService;

    @Autowired
    private ClienteService clienteService;

    // Listar todas las órdenes
    @GetMapping
    public ResponseEntity<List<OrdenPedidoDTO>> listOrdenes() {
        return ResponseEntity.ok(ordenPedidoService.findAll());
    }

    // Obtener una orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrdenById(@PathVariable Long id) {
        OrdenPedidoDTO orden = ordenPedidoService.findById(id);
        if (orden == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orden);
    }

    // Crear una nueva orden
    @PostMapping
    public ResponseEntity<?> createOrden(@RequestBody OrdenPedidoDTO ordenPedidoDTO) {
        try {
            List<OrdenPedidoDTO> creadas = ordenPedidoService.save(ordenPedidoDTO);
            return ResponseEntity.ok(creadas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la orden.");
        }
    }

    // Actualizar una orden existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrden(@PathVariable Long id, @RequestBody OrdenPedidoDTO ordenPedidoDTO) {
        try {
            ordenPedidoDTO.setId(id);
            ordenPedidoService.save(ordenPedidoDTO);
            return ResponseEntity.ok("Orden actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la orden.");
        }
    }

    // Eliminar una orden
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrden(@PathVariable Long id) {
        ordenPedidoService.deleteById(id);
        return ResponseEntity.ok("Orden eliminada exitosamente");
    }

    // Cambiar el estado de la orden
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody EstadoPedidoRequestDTO request) {

        try {
            OrdenPedidoDTO ordenActualizada = ordenPedidoService.cambiarEstado(id, request);
            return ResponseEntity.ok(ordenActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al cambiar el estado de la orden.");
        }
    }

    // Actualizar la fecha de entrega
    @PatchMapping("/{id}/fecha-entrega")
    public ResponseEntity<?> actualizarFechaEntrega(@PathVariable Long id, @RequestParam String nuevaFechaEntrega) {
        try {
            Date nuevaFecha = Date.valueOf(nuevaFechaEntrega);
            OrdenPedidoDTO ordenActualizada = ordenPedidoService.actualizarFechaEntrega(id, nuevaFecha);
            return ResponseEntity.ok(ordenActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la fecha de entrega.");
        }
    }

    @PutMapping("/{ordenId}/asignar-operario/{operarioId}")
    public ResponseEntity<?> asignarOperario(
            @PathVariable Long ordenId,
            @PathVariable Long operarioId
    ) {
        ordenPedidoService.asignarOperario(ordenId, operarioId);
        return ResponseEntity.ok().build();
    }


    // Listar todos los clientes (opcional, si necesitas en el frontend)
    @GetMapping("/clientes")
    public ResponseEntity<?> listClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }
}
