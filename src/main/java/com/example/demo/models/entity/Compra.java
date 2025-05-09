package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detalle;
    private String proveedor;
    private float monto;
    private String estado;
    @Temporal(TemporalType.DATE)
    private Date fecha_compra;

    @Column(nullable = false)
    private int cantidadComprada;

    @ManyToOne
    private Insumo insumo;


}
