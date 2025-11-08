package com.example.demo.models.entity;

import com.example.demo.models.enums.TipoExtintor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private TipoExtintor tipoExtintor;
    private String produccionId;
    private LocalTime horaEntrega;
    private LocalDateTime fechaFin;
    private int cantidad;

    private List<DetallePedidoEmbed> detallePedidos;
    private List<InsumoProduccionEmbed> insumosProduccion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getOperarioId() {
        return operarioId;
    }

    public void setOperarioId(String operarioId) {
        this.operarioId = operarioId;
    }

    public List<DetallePedidoEmbed> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(List<DetallePedidoEmbed> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    public List<InsumoProduccionEmbed> getInsumosProduccion() {
        return insumosProduccion;
    }

    public void setInsumosProduccion(List<InsumoProduccionEmbed> insumosProduccion) {
        this.insumosProduccion = insumosProduccion;
    }

    public TipoExtintor getTipoExtintor() {
        return tipoExtintor;
    }

    public void setTipoExtintor(TipoExtintor tipoExtintor) {
        this.tipoExtintor = tipoExtintor;
    }

    public String getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(String produccionId) {
        this.produccionId = produccionId;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public LocalTime getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(LocalTime horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
