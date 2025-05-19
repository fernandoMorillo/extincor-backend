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
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String produccionId;
    private LocalTime horaEntrega;
    private int cantidad;

    private List<DetallePedidoEmbed> detallePedidos;
    private List<InsumoProduccionEmbed> insumosProduccion;

    public OrdenPedidoDTO(String id, String numeroPedido, String estadoPedido, LocalDateTime fechaEntrega, LocalDateTime fechaPedido, Double montoTotal, String clienteId, String observacion, String operarioId, String tipoServicio, String clienteNombre, String operarioNombre, TipoExtintor tipoExtintor, List<DetallePedidoEmbed> detallePedidos, String produccionId, List<InsumoProduccionEmbed> insumosProduccion, LocalTime horaEntrega, int cantidad) {
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
        this.produccionId = produccionId;
        this.insumosProduccion = insumosProduccion;
        this.horaEntrega = horaEntrega;
        this.cantidad = cantidad;
    }

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

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
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

    public String getOperarioNombre() {
        return operarioNombre;
    }

    public void setOperarioNombre(String operarioNombre) {
        this.operarioNombre = operarioNombre;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
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
