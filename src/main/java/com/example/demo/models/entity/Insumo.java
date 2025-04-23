package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "insumos")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int stock;

    private int cantidad;
    private int unidades;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalleCompras;
}
