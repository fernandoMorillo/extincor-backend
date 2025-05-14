package com.example.demo.models.dto;

import com.example.demo.models.entity.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InsumoDTO {
    private String id;
    private Long idSecuencial;
    private Integer cantidad;
    private LocalDateTime fechaIngreso;
    private String nombre;
    private Integer precioUnitario;
    private Integer stock;
    private Integer stockMinimo;
    private String unidades;

    private List<InsumoProduccion> insumosProduccion;
    private List<OrdenPedidoEmbed> ordenesPedido;
    private List<DetalleCompra> detalleCompras;
}
