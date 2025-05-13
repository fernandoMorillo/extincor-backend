package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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

    @Column(nullable = false)
    private int stock;

    private int cantidad;
    private String unidades;

    private int precioUnitario;
    private int stockMinimo = 15;



    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaIngreso;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalleCompras;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    private List<Compra> compras;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    private List<InsumoProduccion> insumosProduccion;


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
