package com.example.demo.models.entity;

import lombok.Data;

@Data
public class InsumoProduccion {
    private Long id;
    private Long cantidad;
    private Long insumoId;
    private Long produccionId;
    private String estado;
    private Long ordenPedidoId;
}
