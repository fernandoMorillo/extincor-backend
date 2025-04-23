package com.example.demo.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponseDTO {
    private String token;
    private UsuarioDTO usuario;
}
