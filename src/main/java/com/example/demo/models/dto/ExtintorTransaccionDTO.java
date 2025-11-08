package com.example.demo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtintorTransaccionDTO {

    private String tipoServicio; // recarga, venta
    private String tipoExtint; // ABC, CO2, TIPO_K, AGUA
    private Integer cantidad;
    private Integer diasHastaEntrega;
    private String diaSemana; // Lunes, Martes, etc.
    private String mes; // nombre del mes
    private String rangoMonto; // Alto, Medio, Bajo
    private String clienteFrecuente; // Si, No
    private Integer stockDisponible;

    // Campo predicho
    private String tipoError; // sin_error, cantidad_incorrecta, sin_stock, etc.
    private Double confidencePrediction; // confianza de la predicción

}
