    package com.example.demo.models.entity;

    import jakarta.persistence.*;
    import lombok.Data;

    @Data
    @Entity
    @Table(name = "envases")
    public class Envase {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String tipo;
        private float capacidad;

        @ManyToOne
        @JoinColumn(name = "producto_id")
        private Producto producto;
    }
