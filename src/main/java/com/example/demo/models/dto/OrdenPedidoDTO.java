package com.example.demo.models.dto;

import com.example.demo.models.entity.DetallePedidoEmbed;
import com.example.demo.models.entity.InsumoProduccionEmbed;
import com.example.demo.models.enums.TipoExtintor;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class OrdenPedidoDTO {
    private String id;
    private String numeroPedido;
    private String estadoPedido;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaPedido;
    private Double montoTotal;
    private String clienteId;
    private String observacion;
    private String operarioId;
    private String tipoServicio;
    private String clienteNombre;
    private String operarioNombre;
    private TipoExtintor tipoExtintor;

    private List<DetallePedidoEmbed> detallePedidos;
    private List<InsumoProduccionEmbed> insumosProduccion;

    public OrdenPedidoDTO(String id, String numeroPedido, String estadoPedido, LocalDateTime fechaEntrega, LocalDateTime fechaPedido, Double montoTotal, String clienteId, String observacion, String operarioId, String tipoServicio, String clienteNombre, String operarioNombre, TipoExtintor tipoExtintor, List<DetallePedidoEmbed> detallePedidos, List<InsumoProduccionEmbed> insumosProduccion) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.estadoPedido = estadoPedido;
        this.fechaEntrega = fechaEntrega;
        this.fechaPedido = fechaPedido;
        this.montoTotal = montoTotal;
        this.clienteId = clienteId;
        this.observacion = observacion;
        this.operarioId = operarioId;
        this.tipoServicio = tipoServicio;
        this.clienteNombre = clienteNombre;
        this.operarioNombre = operarioNombre;
        this.tipoExtintor = tipoExtintor;
        this.detallePedidos = detallePedidos;
        this.insumosProduccion = insumosProduccion;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public void setOperarioId(String operarioId) {
        this.operarioId = operarioId;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOperarioNombre(String operarioNombre) {
        this.operarioNombre = operarioNombre;
    }

    public TipoExtintor getTipoExtintor() {
        return tipoExtintor;
    }

    public void setTipoExtintor(TipoExtintor tipoExtintor) {
        this.tipoExtintor = tipoExtintor;
    }
}
