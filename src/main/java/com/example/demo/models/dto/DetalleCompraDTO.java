package com.example.demo.models.dto;

import lombok.Data;

@Data
public class DetalleCompraDTO {
    private Long id;
    private Integer cantidad;
    private CompraDTO compraId; // Referencia al objeto CompraDTO, pero solo se usará su ID
    private InsumoDTO insumo;
}
