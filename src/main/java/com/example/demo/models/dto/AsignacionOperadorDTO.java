package com.example.demo.models.dto;

public class AsignacionOperadorDTO {
    private String ordenId;
    private String operadorId;


    public AsignacionOperadorDTO(String ordenId, String operadorId) {
        this.ordenId = ordenId;
        this.operadorId = operadorId;
    }

    public String getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(String ordenId) {
        this.ordenId = ordenId;
    }

    public String getOperadorId() {
        return operadorId;
    }

    public void setOperadorId(String operadorId) {
        this.operadorId = operadorId;
    }
}
