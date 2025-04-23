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
}
