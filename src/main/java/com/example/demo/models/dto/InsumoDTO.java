package com.example.demo.models.dto;

import lombok.Data;

@Data
public class InsumoDTO {
    private Long id;
    private String nombre;
    private int stock;
    private int cantidad;
    private int unidades;
}
