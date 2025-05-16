package com.example.demo.controller;

import com.example.demo.mapper.OrdenPedidoMapper;
import com.example.demo.models.dto.AsignacionOperadorDTO;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.services.OrdenPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<OrdenPedidoDTO> crear(@RequestBody OrdenPedidoDTO dto) {
        return ResponseEntity.ok(service.crearOrden(dto));
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



}