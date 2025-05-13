package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "insumos-produccion")
public class InsumoProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "produccion_id")
    private Produccion produccion;


    @ManyToOne
    @JoinColumn(name = "orden_pedido_id")
    private OrdenPedido ordenPedido;
}
