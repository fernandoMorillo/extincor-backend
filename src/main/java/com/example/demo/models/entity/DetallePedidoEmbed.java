package com.example.demo.models.entity;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetallePedidoEmbed {
    private Long id;
    private Integer cantidad;
    private Long ordenPedidoId;
    private Long produccionId;
    private Long productoId;
    private Long total;


    public DetallePedidoEmbed() {}

    public DetallePedidoEmbed(Long id, Integer cantidad, Long ordenPedidoId, Long produccionId, Long productoId, Long total) {
        this.id = id;
        this.cantidad = cantidad;
        this.ordenPedidoId = ordenPedidoId;
        this.produccionId = produccionId;
        this.productoId = productoId;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getOrdenPedidoId() {
        return ordenPedidoId;
    }

    public void setOrdenPedidoId(Long ordenPedidoId) {
        this.ordenPedidoId = ordenPedidoId;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(Long produccionId) {
        this.produccionId = produccionId;
    }
}
