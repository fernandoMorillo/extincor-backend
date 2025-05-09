package com.example.demo.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InsumoDTO {
    private Long id;
    private String nombre;
    private int stock;
    private int cantidad;
    private String unidades;
    private int precioUnitario;
    private int stockMinimo = 15;
    private Date fechaIngreso;
}
