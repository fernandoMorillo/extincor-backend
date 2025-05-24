package com.example.demo.models.dto;

import com.example.demo.models.entity.DetallePedido;
import com.example.demo.models.entity.Envase;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductoDTO {
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
