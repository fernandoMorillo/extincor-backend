package com.example.demo.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdministradorDTO extends UsuarioDTO {
    private String telefono;
}
