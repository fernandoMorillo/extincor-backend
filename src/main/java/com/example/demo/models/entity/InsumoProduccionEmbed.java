package com.example.demo.models.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsumoProduccionEmbed {
    private Long id;
    private Integer cantidad;
    private Long insumoId;
    private Long produccionId;
    private String estado;
    private Long ordenPedidoId;

    public InsumoProduccionEmbed(Long id, Integer cantidad, Long insumoId, Long produccionId, String estado, Long ordenPedidoId) {
        this.id = id;
        this.cantidad = cantidad;
        this.insumoId = insumoId;
        this.produccionId = produccionId;
        this.estado = estado;
        this.ordenPedidoId = ordenPedidoId;
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

    public Long getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Long insumoId) {
        this.insumoId = insumoId;
    }

    public Long getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(Long produccionId) {
        this.produccionId = produccionId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getOrdenPedidoId() {
        return ordenPedidoId;
    }

    public void setOrdenPedidoId(Long ordenPedidoId) {
        this.ordenPedidoId = ordenPedidoId;
    }
}
