package com.example.demo.controller;

import com.example.demo.mapper.OrdenPedidoMapper;
import com.example.demo.models.dto.InsumoProduccionDTO;
import com.example.demo.models.dto.OrdenFinalizacionDTO;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.services.OrdenPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes-pedido")
public class OrdenPedidoController {

    @Autowired
    private OrdenPedidoService service;
    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrdenPedidoMapper mapper;
    @Autowired
    private OrdenPedidoService ordenPedidoService;


    @PostMapping
    public ResponseEntity<?> crear(@RequestBody OrdenPedidoDTO dto) {
        try {
            return ResponseEntity.ok(service.crearOrden(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping
    public Page<OrdenPedidoDTO> listar(Pageable pageable) {
        return ordenPedidoRepository.findAll(pageable)
                .map(orden -> {
                    OrdenPedidoDTO dto = mapper.toDTO(orden);

                    if (orden.getClienteId() != null) {
                        usuarioRepository.findById(orden.getClienteId())
                                .ifPresent(usuario -> dto.setClienteNombre(usuario.getNombre()));
                    }

                    if (orden.getOperarioId() != null) {
                        usuarioRepository.findById(orden.getOperarioId())
                                .ifPresent(usuario -> dto.setOperarioNombre(usuario.getNombre()));
                    }

                    return dto;
                });
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrdenPedidoDTO> obtenerPorId(@PathVariable String id) {
        OrdenPedidoDTO dto = service.obtenerOrdenPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{ordenId}/asignar-operador/{operadorId}")
    public ResponseEntity<String> asignarOperador(
            @PathVariable String ordenId,
            @PathVariable String operadorId) {

        Optional<OrdenPedido> optionalOrden = ordenPedidoRepository.findById(ordenId);

        if (optionalOrden.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Orden no encontrada");
        }

        OrdenPedido orden = optionalOrden.get();
        orden.setOperarioId(operadorId);
        ordenPedidoRepository.save(orden);

        return ResponseEntity.ok("Operador asignado correctamente");
    }

    @PostMapping("/{ordenId}/producciones/{produccionId}/insumos")
    public ResponseEntity<String> agregarInsumosProduccion(
            @PathVariable String ordenId,
            @PathVariable String produccionId,
            @RequestBody List<InsumoProduccionDTO> insumosDTO) {

        ordenPedidoService.agregarInsumosProduccion(ordenId, produccionId, insumosDTO);
        return ResponseEntity.ok("Insumos agregados correctamente a la orden y a la producción.");
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstado(@PathVariable String id, @RequestBody Map<String, String> estadoRequest) {
        String nuevoEstado = estadoRequest.get("estadoPedido");
        ordenPedidoService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok("Estado actualizado correctamente");
    }

    @PutMapping("/actualizar-orden/{id}")
    public ResponseEntity<OrdenPedidoDTO> actualizarOrden(@PathVariable String id, @RequestBody OrdenPedidoDTO dto) {
        OrdenPedidoDTO actualizada = ordenPedidoService.actualizarOrdenPedido(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminar-orden/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable String id) {
        ordenPedidoService.eliminarOrdenPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count-by-fecha-entrega")
    public ResponseEntity<Long> contarOrdenesPorFechaEntrega(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        long count = ordenPedidoService.contarOrdenesPorFechaEntrega(fecha);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/finalizar-orden/{id}")
    public ResponseEntity<?> finalizarOrden(
            @PathVariable String id,
            @RequestBody OrdenFinalizacionDTO dto) {
        try {
            if (dto.getCorreoCliente() == null || dto.getCorreoCliente().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("mensaje", "El correo del cliente no puede estar vacío."));
            }

            if (dto.getNombreCliente() == null || dto.getNombreCliente().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("mensaje", "El nombre del cliente no puede estar vacío."));
            }

            OrdenPedidoDTO ordenFinalizada = ordenPedidoService.finalizarOrden(id, dto);
            return ResponseEntity.ok(ordenFinalizada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Orden no encontrada."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error inesperado al finalizar la orden."));
        }
    }



}