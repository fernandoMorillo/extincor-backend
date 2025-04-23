package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "produccion")
public class Produccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_produccion", unique = true, nullable = false)
    private String codigoProduccion;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    private int cantidad_producida;
    private String producto_nombre;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "operarios_id", referencedColumnName = "id")
    private OperarioIngreso operario;
}
