package com.example.demo.models.dto;

import lombok.Data;

@Data
public class EnvaceDTO {

    private Long id;
    private String nombre;

    public EnvaceDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
