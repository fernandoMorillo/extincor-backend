    package com.example.demo.models.entity;

    import jakarta.persistence.*;
    import lombok.Data;

    @Data
    @Entity
    @Table(name = "detalle_pedido")
    @Inheritance
    public class DetallePedido {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private int cantidad;

        @ManyToOne
        @JoinColumn(name = "ordenpedido_id", nullable = false)
        private OrdenPedido ordenpedido;

        @ManyToOne
        @JoinColumn(name = "produccion_id", nullable = true)
        private Produccion produccion;

        @ManyToOne
        @JoinColumn(name = "producto_id", nullable = false)
        private Producto producto;
    }
