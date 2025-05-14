package com.example.demo.controller;

import com.example.demo.models.dto.UsuarioDTO;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public UsuarioDTO findById(@PathVariable String id) {
        return usuarioService.findById(id);
    }

    @PostMapping
    public UsuarioDTO save(@RequestBody UsuarioDTO dto) {
        return usuarioService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        usuarioService.deleteById(id);
    }
}
