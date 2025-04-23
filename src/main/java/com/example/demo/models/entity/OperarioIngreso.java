package com.example.demo.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("OPERARIO")
public class OperarioIngreso extends Usuario {
    private String direccion;
    private String telefono;
    private String especialidad; // Asegúrate de que esté definido
    private String estado; // Agregar estado si es necesario

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ventana_id") // Clave foránea hacia `OperarioVentana`
    private OperarioVentana ventana;


    @OneToMany(mappedBy = "operario", cascade = CascadeType.ALL)
    private List<Produccion> producciones;
}
