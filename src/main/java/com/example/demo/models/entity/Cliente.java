package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@DiscriminatorValue("CLIENTE") // Valor para la columna de discriminación
public class Cliente extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L; // UID recomendado

    private String direccion;
    private String telefono;

    @Column(name = "tipo_cliente")
    private String tipo_cliente;


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo_cliente() {
        return tipo_cliente;
    }

    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }
}
