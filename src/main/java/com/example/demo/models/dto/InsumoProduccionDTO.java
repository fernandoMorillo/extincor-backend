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
    private String id;
    private int cantidad;
    private String insumoId;
    private String produccionId;
    private String estado;
    private String ordenPedidoId;
}
