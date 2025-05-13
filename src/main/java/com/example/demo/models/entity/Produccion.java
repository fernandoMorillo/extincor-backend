package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
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


    private LocalDateTime fechaInicio;


    private LocalDateTime fechaFin;

    private int cantidad_producida;
    private String producto_nombre;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "operario_id", referencedColumnName = "id")
    private OperarioIngreso operario;

    @OneToMany(mappedBy = "produccion",  cascade = CascadeType.ALL)
    private List<InsumoProduccion> insumosProduccion;

    @OneToOne
    @JoinColumn(name = "orden_pedido_id")
    private OrdenPedido ordenPedido;

}
