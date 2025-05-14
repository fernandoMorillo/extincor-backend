package com.example.demo.models.entity;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Envase {
    private Long id;
    private Double capacidad;
    private String tipo;
    private Long productoId;
}
