package com.example.demo.controller;

import com.example.demo.exceptions.CorreoYaExisteException;
import com.example.demo.models.dto.ClienteDTO;
import com.example.demo.services.ClienteService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Listar todos los clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    /**
     * Obtener cliente por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    /**
     * Crear nuevo cliente
     */
    @PostMapping
    public ResponseEntity<?> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteService.save(clienteDTO);
            return ResponseEntity.ok("Cliente creado exitosamente");
        } catch (CorreoYaExisteException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Actualizar cliente existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteDTO.setId(id);
            clienteService.save(clienteDTO);
            return ResponseEntity.ok("Cliente actualizado exitosamente");
        } catch (CorreoYaExisteException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Eliminar cliente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.ok("Cliente eliminado exitosamente");
    }
}
