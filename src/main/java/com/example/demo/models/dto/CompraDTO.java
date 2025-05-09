package com.example.demo.models.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CompraDTO {
    private Long id;
    private String detalle;
    private String proveedor;
    private Date fecha_compra;
    private float monto;
    private String estado;
    private Long insumo_id;
    private int cantidadComprada;

    public Long getInsumo_id() {
        return insumo_id;
    }

    public void setInsumo_id(Long insumo_id) {
        this.insumo_id = insumo_id;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }
}
