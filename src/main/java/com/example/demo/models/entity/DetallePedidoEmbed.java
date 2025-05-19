package com.example.demo.models.entity;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetallePedidoEmbed {
    private String id;
    private Integer cantidad;
    private String ordenPedidoId;
    private String produccionId;
    private String productoId;
    private String total;


    public DetallePedidoEmbed() {}

    public DetallePedidoEmbed(String id, Integer cantidad, String ordenPedidoId, String produccionId, String total, String productoId) {
        this.id = id;
        this.cantidad = cantidad;
        this.ordenPedidoId = ordenPedidoId;
        this.produccionId = produccionId;
        this.total = total;
        this.productoId = productoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getOrdenPedidoId() {
        return ordenPedidoId;
    }

    public void setOrdenPedidoId(String ordenPedidoId) {
        this.ordenPedidoId = ordenPedidoId;
    }

    public String getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(String produccionId) {
        this.produccionId = produccionId;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
