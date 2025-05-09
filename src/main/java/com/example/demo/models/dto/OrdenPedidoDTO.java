package com.example.demo.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.util.Date;

@Data
public class OrdenPedidoDTO {
    private Long id;
    private String numeroPedido; // Cambiado a String

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Bogota")
    private Date fechaPedido;
    private String estadoPedido;
    private float montoTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Bogota")
    private Date fechaEntrega;
    private ClienteDTO cliente;
    private String observacion;

    private String tipoServicio;
    private Integer cantidadExtintores;

    private OperarioIngresoDto operario;
}
