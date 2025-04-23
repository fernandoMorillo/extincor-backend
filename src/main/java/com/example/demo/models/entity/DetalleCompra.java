package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_compra")
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;
}
