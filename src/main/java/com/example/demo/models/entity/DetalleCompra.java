package com.example.demo.models.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompra {
    private Long id;
    private Integer cantidad;
    private Long compraId;
    private Long insumoId;
}
