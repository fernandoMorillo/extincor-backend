package com.example.demo.models.entity;

public class ExtintorPrediccion {

    private String tipoServicio; // {recarga, venta}
    private String tipoExtintor; // {TIPO_K, AGUA, ABC, CO2}
    private double cantidad; // numérico
    private double diasHastaEntrega; // numérico
    private String diaSemana; // {Jueves, Miercoles, Sabado, Viernes, Domingo, Lunes, Martes}
    private int mes; // numérico
    private String rangoMonto; // {Medio, Alto, Bajo}
    private String clienteFrecuente; // {Si, No}
    private double stockDisponible; // numérico

    public ExtintorPrediccion() {
    }

    public ExtintorPrediccion(String tipoServicio, String tipoExtintor, double cantidad, double diasHastaEntrega,
            String diaSemana, int mes, String rangoMonto, String clienteFrecuente, double stockDisponible) {
        this.tipoServicio = tipoServicio;
        this.tipoExtintor = tipoExtintor;
        this.cantidad = cantidad;
        this.diasHastaEntrega = diasHastaEntrega;
        this.diaSemana = diaSemana;
        this.mes = mes;
        this.rangoMonto = rangoMonto;
        this.clienteFrecuente = clienteFrecuente;
        this.stockDisponible = stockDisponible;
    }

    // Getters y Setters
    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getTipoExtintor() {
        return tipoExtintor;
    }

    public void setTipoExtintor(String tipoExtintor) {
        this.tipoExtintor = tipoExtintor;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getDiasHastaEntrega() {
        return diasHastaEntrega;
    }

    public void setDiasHastaEntrega(double diasHastaEntrega) {
        this.diasHastaEntrega = diasHastaEntrega;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getRangoMonto() {
        return rangoMonto;
    }

    public void setRangoMonto(String rangoMonto) {
        this.rangoMonto = rangoMonto;
    }

    public String getClienteFrecuente() {
        return clienteFrecuente;
    }

    public void setClienteFrecuente(String clienteFrecuente) {
        this.clienteFrecuente = clienteFrecuente;
    }

    public double getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(double stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    @Override
    public String toString() {
        return "ExtintorPrediccion{" +
                "tipoServicio='" + tipoServicio + '\'' +
                ", tipoExtintor='" + tipoExtintor + '\'' +
                ", cantidad=" + cantidad +
                ", diasHastaEntrega=" + diasHastaEntrega +
                ", diaSemana='" + diaSemana + '\'' +
                ", mes=" + mes +
                ", rangoMonto='" + rangoMonto + '\'' +
                ", clienteFrecuente='" + clienteFrecuente + '\'' +
                ", stockDisponible=" + stockDisponible +
                '}';
    }
}
