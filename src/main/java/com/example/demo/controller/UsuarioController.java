package com.example.demo.controller;

import com.example.demo.exceptions.CorreoYaExisteException;
import com.example.demo.models.dto.UsuarioDTO;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping("/listado-usuarios")
    public ResponseEntity<List<UsuarioDTO>> listUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // Guardar o actualizar un usuario
    @PostMapping
    public ResponseEntity<?> saveOrUpdateUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO savedUsuario = usuarioService.save(usuarioDTO);
            return ResponseEntity.ok(savedUsuario);
        } catch (CorreoYaExisteException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
