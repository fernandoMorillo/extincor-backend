package com.example.demo.models.entity;

import lombok.Data;

@Data
public class InsumoProduccion {
    private String id;
    private int cantidad;
    private String insumoId;
    private String produccionId;
    private String estado;
    private String ordenPedidoId;
}
