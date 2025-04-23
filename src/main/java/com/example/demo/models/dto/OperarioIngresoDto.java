package com.example.demo.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OperarioIngresoDto extends UsuarioDTO {
    private String direccion;
    private String telefono;
    private String especialidad; // Agregar esta propiedad
    private String estado; // Agregar estado si se usa en la vista

    private List<ProduccionDTO> producciones;
}
