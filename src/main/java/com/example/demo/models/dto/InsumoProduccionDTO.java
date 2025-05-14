package com.example.demo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoProduccionDTO {
    private Long id;
    private int cantidad;
    private InsumoDTO insumo;        // Objeto Insumo para incluir detalles del insumo
    private ProduccionDTO produccion; // Objeto Produccion para incluir detalles de la producción
    private String estado;
    private String ordenPedidoId;
}
