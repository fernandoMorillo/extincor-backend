package com.example.demo.models.dto;

import com.example.demo.models.entity.OrdenPedido;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UsuarioDTO {
    private String id;
    private String tipoUsuario;
    private Long idSecuencial;
    private String correo;
    private Date fechaCreacion;
    private String nombre;
    private String password;
    private String telefono;
    private String direccion;
    private String especialidad;
    private String estado;

    private Long ventanaId;
    private String tipoCliente;


    private List<OrdenPedido> ordenesPedidos;
}
