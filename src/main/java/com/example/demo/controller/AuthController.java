package com.example.demo.controller;

import com.example.demo.models.dto.AuthRequest;
import com.example.demo.models.dto.AuthResponse;
import com.example.demo.models.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository; // Inyectamos el repositorio

    public AuthController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // Buscar usuario en la base de datos
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(authRequest.getCorreo());

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        // Verificar contraseña
        if (!authRequest.getPassword().equals(usuario.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Generar token JWT
        String token = jwtUtil.generateToken(usuario.getNombre());
        AuthResponse response = new AuthResponse(token, usuario.getCorreo(), usuario.getTipoUsuario());

        return ResponseEntity.ok(response);
    }
}
