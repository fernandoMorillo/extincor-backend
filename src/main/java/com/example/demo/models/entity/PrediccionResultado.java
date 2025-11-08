package com.example.demo.models.entity;

import java.util.Arrays;

public class PrediccionResultado {

    private ExtintorPrediccion entrada;
    private String tipoErrorPredicho;
    private double[] probabilidades;

    public PrediccionResultado() {
    }

    public PrediccionResultado(ExtintorPrediccion entrada, String tipoErrorPredicho, double[] probabilidades) {
        this.entrada = entrada;
        this.tipoErrorPredicho = tipoErrorPredicho;
        this.probabilidades = probabilidades;
    }

    public ExtintorPrediccion getEntrada() {
        return entrada;
    }

    public void setEntrada(ExtintorPrediccion entrada) {
        this.entrada = entrada;
    }

    public String getTipoErrorPredicho() {
        return tipoErrorPredicho;
    }

    public void setTipoErrorPredicho(String tipoErrorPredicho) {
        this.tipoErrorPredicho = tipoErrorPredicho;
    }

    public double[] getProbabilidades() {
        return probabilidades;
    }

    public void setProbabilidades(double[] probabilidades) {
        this.probabilidades = probabilidades;
    }

    @Override
    public String toString() {
        return "PrediccionResultado{" +
                "entrada=" + entrada +
                ", tipoErrorPredicho='" + tipoErrorPredicho + '\'' +
                ", probabilidades=" + Arrays.toString(probabilidades) +
                '}';
    }
}
