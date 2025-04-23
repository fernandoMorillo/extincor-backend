package com.example.demo.models.dto;

import com.example.demo.entities.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String password; // Campo de contraseña
    private Set<Role> tipoUsuario; // Campo para el tipo de usuario (por ejemplo, "ADMINISTRADOR", "CLIENTE")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion = new Date(); // Fecha de creación predeterminada a la fecha actual
}
