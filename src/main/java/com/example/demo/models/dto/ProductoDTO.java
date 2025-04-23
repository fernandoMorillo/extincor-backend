package com.example.demo.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductoDTO {
    private Long id;
    private String codigo; // Campo para el código único
    private String nombre;
    private float precio;
    private String tipo;
    private float capacidad;
    private Date fecha_fabricacion;
    private String estado;
}
