package com.example.demo.controller;

import com.example.demo.models.dto.UsuarioDTO;
import com.example.demo.models.entity.Usuario;
import com.example.demo.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdminController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping
    public List<UsuarioDTO> obtenerAdministradores() {
        return usuarioService.findByTipoUsuario("ADMINISTRADOR");
    }

    @PostMapping
    public Usuario crearAdministrador(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuarioConRol(usuario, "ADMINISTRADOR");
    }
}
