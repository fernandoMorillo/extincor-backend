    package com.example.demo.models.entity;

    import com.example.demo.models.entity.Usuario;
    import jakarta.persistence.DiscriminatorValue;
    import jakarta.persistence.Entity;
    import jakarta.persistence.Column;
    import lombok.Data;
    import lombok.EqualsAndHashCode;

    @Data
    @EqualsAndHashCode(callSuper = true)
    @Entity
    @DiscriminatorValue("ADMINISTRADOR")
    public class Administrador extends Usuario {

        private String telefono;


    }
