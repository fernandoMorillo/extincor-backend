package com.example.demo.models.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    private String tipoUsuario;
    private Long idSecuencial;
    private String correo;
    private Date fechaCreacion;
    private String nombre;
    private String password;
    @Field("telefono_usuario")
    private String telefono;
    @Field("direccion_usuario")
    private String direccion;
    @Field("especialidad_usuario")
    private String especialidad;
    @Field("estado_usuario")
    private String estado;

    private Long ventanaId;
    private String tipoCliente;



    private List<OrdenPedido> ordenesPedidos;


}
