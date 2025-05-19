package com.example.demo.models.dto;

import com.example.demo.models.entity.*;
import com.example.demo.models.enums.TipoExtintor;
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
    private TipoExtintor tiposExtintor;


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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Integer precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public List<InsumoProduccion> getInsumosProduccion() {
        return insumosProduccion;
    }

    public void setInsumosProduccion(List<InsumoProduccion> insumosProduccion) {
        this.insumosProduccion = insumosProduccion;
    }

    public List<OrdenPedidoEmbed> getOrdenesPedido() {
        return ordenesPedido;
    }

    public void setOrdenesPedido(List<OrdenPedidoEmbed> ordenesPedido) {
        this.ordenesPedido = ordenesPedido;
    }

    public List<DetalleCompra> getDetalleCompras() {
        return detalleCompras;
    }

    public void setDetalleCompras(List<DetalleCompra> detalleCompras) {
        this.detalleCompras = detalleCompras;
    }

    public TipoExtintor getTiposExtintor() {
        return tiposExtintor;
    }

    public void setTiposExtintor(TipoExtintor tiposExtintor) {
        this.tiposExtintor = tiposExtintor;
    }
}
