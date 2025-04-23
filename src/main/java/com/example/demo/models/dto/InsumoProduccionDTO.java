package com.example.demo.models.dto;

import lombok.Data;

@Data
public class InsumoProduccionDTO {
    private Long id;
    private int cantidad;
    private InsumoDTO insumo;        // Objeto Insumo para incluir detalles del insumo
    private ProduccionDTO produccion; // Objeto Produccion para incluir detalles de la producción
}
