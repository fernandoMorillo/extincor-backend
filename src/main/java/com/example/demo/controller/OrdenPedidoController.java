package com.example.demo.controller;

import com.example.demo.mapper.OrdenPedidoMapper;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.services.OrdenPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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


    @PostMapping
    public ResponseEntity<OrdenPedidoDTO> crear(@RequestBody OrdenPedidoDTO dto) {
        return ResponseEntity.ok(service.crearOrden(dto));
    }

    @GetMapping
    public List<OrdenPedidoDTO> listar() {
        return ordenPedidoRepository.findAll().stream().map(orden -> {
            OrdenPedidoDTO dto = mapper.toDTO(orden); // usa el mapper correctamente

            // Agrega el nombre del cliente si existe
            usuarioRepository.findById(orden.getClienteId())
                    .ifPresent(usuario -> {
                        dto.setClienteNombre(usuario.getNombre());
                        System.out.println("Cliente encontrado: " + usuario.getNombre()); // Depuración
                    });

            // Depuración para verificar si otros campos están vacíos
            System.out.println("Detalle pedidos: " + orden.getDetallePedidos());
            System.out.println("Estado pedido: " + orden.getEstadoPedido());

            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenPedidoDTO> obtenerPorId(@PathVariable String id) {
        OrdenPedidoDTO dto = service.obtenerOrdenPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }


}
