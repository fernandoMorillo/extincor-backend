package com.example.demo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrediccionResponse {

    private String tipoErrorPredicho;
    private Double confidence;
    private Boolean riesgoDeError; // true si se predice algún error
    private String recomendacion;
    private Map<String, Double> distribucionProbabilidades; // todas las probabilidades

    // Datos de entrada para referencia
    private ExtintorTransaccionDTO datosEntrada;

}
