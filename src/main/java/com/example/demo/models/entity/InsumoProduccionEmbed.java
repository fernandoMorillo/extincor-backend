package com.example.demo.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsumoProduccionEmbed {
    private String id;
    private Integer cantidad;
    private String insumoId;
    private String produccionId;
    private String estado;
    private String ordenPedidoId;


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

    public String getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(String insumoId) {
        this.insumoId = insumoId;
    }

    public String getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(String produccionId) {
        this.produccionId = produccionId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrdenPedidoId() {
        return ordenPedidoId;
    }

    public void setOrdenPedidoId(String ordenPedidoId) {
        this.ordenPedidoId = ordenPedidoId;
    }
}
