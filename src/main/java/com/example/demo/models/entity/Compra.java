package com.example.demo.models.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "compras")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Compra {
    @Id
    private String id;
    private Long idSecuencial;
    private String detalle;
    private String estado;
    private Date fechaCompra;
    private Double monto;
    private String proveedor;
    private Integer cantidadComprada;
    private Long insumoId;

    private List<DetalleCompra> detalleCompras;
}
