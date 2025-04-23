package com.example.demo.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClienteDTO extends UsuarioDTO {
    private String direccion;
    private String telefono;

}
