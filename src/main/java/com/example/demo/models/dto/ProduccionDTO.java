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

    private List<DetallePedidoEmbed> detallePedidos;
    private List<InsumoProduccionEmbed> insumosProduccion;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdSecuencial() {
        return idSecuencial;
    }

    public void setIdSecuencial(Long idSecuencial) {
        this.idSecuencial = idSecuencial;
    }

    public String getCodigoProduccion() {
        return codigoProduccion;
    }

    public void setCodigoProduccion(String codigoProduccion) {
        this.codigoProduccion = codigoProduccion;
    }

    public Integer getCantidadProducida() {
        return cantidadProducida;
    }

    public void setCantidadProducida(Integer cantidadProducida) {
        this.cantidadProducida = cantidadProducida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public String getOperarioId() {
        return operarioId;
    }

    public void setOperarioId(String operarioId) {
        this.operarioId = operarioId;
    }

    public String getOrdenPedidoId() {
        return ordenPedidoId;
    }

    public void setOrdenPedidoId(String ordenPedidoId) {
        this.ordenPedidoId = ordenPedidoId;
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
}
