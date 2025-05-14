package com.example.demo.controller;

import com.example.demo.models.dto.UsuarioDTO;
import com.example.demo.models.entity.Usuario;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operadores")
public class OperadorController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping
    public List<UsuarioDTO> obtenerOperadores() {
        return usuarioService.findByTipoUsuario("OPERADOR");
    }

    @PostMapping
    public Usuario crearOperador(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuarioConRol(usuario, "OPERADOR");
    }
}
