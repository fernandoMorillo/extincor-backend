package com.example.demo.models.dto;

import com.example.demo.models.entity.DetalleCompra;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CompraDTO {
    private String id;
    private Long idSecuencial;
    private String detalle;
    private String estado;
    private Date fechaCompra;
    private Double monto;
    private String proveedor;
    private Integer cantidadComprada;
    private String insumoId;

    private List<DetalleCompra> detalleCompras;
}
