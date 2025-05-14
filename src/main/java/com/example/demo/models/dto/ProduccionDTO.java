package com.example.demo.models.dto;

import com.example.demo.models.entity.DetallePedidoEmbed;
import com.example.demo.models.entity.InsumoProduccionEmbed;
import com.example.demo.models.entity.OrdenPedidoEmbed;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProduccionDTO {
    private String id;
    private Long idSecuencial;
    private Integer cantidadProducida;
    private String codigoProduccion;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String productoNombre;
    private String operarioId;
    private String ordenPedidoId;

    private OrdenPedidoEmbed ordenesPedido;
    private List<DetallePedidoEmbed> detallePedidos;
    private List<InsumoProduccionEmbed> insumosProduccion;
}
