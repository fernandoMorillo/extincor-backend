package com.example.demo.models.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "productos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id
    private String id;
    private Long idSecuencial;
    private Double capacidad;
    private String codigo;
    private String estado;
    private LocalDateTime fechaFabricacion;
    private String nombre;
    private Double precio;
    private String tipo;

    private List<DetallePedido> detallePedidos;
    private List<Envase> envases;
}
