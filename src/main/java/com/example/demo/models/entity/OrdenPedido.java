package com.example.demo.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "ordenesPedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenPedido {
    private String id;
    private String estadoPedido;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaPedido;
    private Double montoTotal;
    private String numeroPedido;
    private String clienteId;
    private String observacion;
    private String operarioId;
    private String tipoServicio;

    private List<DetallePedidoEmbed> detallePedidos;
    private List<InsumoProduccionEmbed> insumosProduccion;
}
