package com.example.demo.models.entity;

import com.example.demo.models.enums.TipoExtintor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "insumos")
public class Insumo {

    @Id
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
    private List<TipoExtintor> tiposExtintor;
}
