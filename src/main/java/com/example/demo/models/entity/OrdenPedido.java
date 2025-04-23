package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ordenes_pedido")
public class OrdenPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", unique = true, nullable = false)
    private Long numeroPedido;


    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_pedido")
    private Date fechaPedido;

    @Column(name = "estado_pedido", nullable = false)
    private String estadoPedido;

    @Column(name = "monto_total")
    private float montoTotal;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

}
