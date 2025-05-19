package com.example.demo.models.entity;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetallePedido {
    private String id;
    private Integer cantidad;
    private String ordenPedidoId;
    private String produccionId;
    private String productoId;
}
