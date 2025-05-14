package com.example.demo.models.entity;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetallePedido {
    private Long id;
    private Integer cantidad;
    private Long ordenPedidoId;
    private Long produccionId;
    private Long productoId;
}
