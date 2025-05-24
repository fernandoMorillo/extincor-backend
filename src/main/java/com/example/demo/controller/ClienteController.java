package com.example.demo.controller;

import com.example.demo.models.dto.UsuarioDTO;
import com.example.demo.models.entity.Usuario;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceimp;

    @GetMapping
    public List<UsuarioDTO> obtenerClientes() {
        return usuarioServiceimp.findByTipoUsuario("CLIENTE");
    }

    @PostMapping
    public Usuario crearCliente(@RequestBody Usuario usuario) {
        return usuarioServiceimp.guardarUsuarioConRol(usuario, "CLIENTE");
    }

    @PutMapping("/{id}")
    public UsuarioDTO update(@PathVariable String id, @RequestBody UsuarioDTO dto) {
        dto.setId(id);
        return usuarioServiceimp.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        usuarioServiceimp.deleteById(id);
    }
}
