package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OperarioVentana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String ubicacion;

    @OneToOne(mappedBy = "ventana")
    private OperarioIngreso operario;

    // otros atributos y métodos
}
