package com.example.demo.models.dto;

import lombok.Data;

@Data
public class EnvaseDTO {
    private Long id;
    private String tipo;
    private float capacidad;
    private ProductoDTO producto; // Agrega el producto asociado
}
