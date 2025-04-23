package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Código único y obligatorio
    private String codigo;

    private String nombre;
    private float precio;
    private String tipo;
    private float capacidad;

    @Temporal(TemporalType.DATE)
    private Date fecha_fabricacion;

    private String estado;
}
