package com.example.demo.models.entity;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.models.enums.TipoExtintor;
import lombok.Data;

@Data
public class OrdenPedidoEmbed {

    private Long id;
    private String estadoPedido;
    private LocalDate fechaEntrega;
    private LocalDate fechaPedido;
    private Double montoTotal;
    private Long numeroPedido;
    private Long clienteId;
    private String observacion;
    private Long operarioId;
    private String tipoServicio;
    private TipoExtintor tipoExtintor;
    private List<DetallePedidoEmbed> detallePedidos;
}
