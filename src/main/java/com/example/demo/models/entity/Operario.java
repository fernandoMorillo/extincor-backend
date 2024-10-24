package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "operario")
public class Operario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String especialidad;
    private String estado;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Produccion> produccion;
}
