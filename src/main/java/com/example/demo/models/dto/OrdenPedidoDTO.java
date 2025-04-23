package com.example.demo.models.dto;

import lombok.Data;
import java.util.Date;

@Data
public class OrdenPedidoDTO {
    private Long id;
    private String numeroPedido; // Cambiado a String
    private Date fechaPedido;
    private String estadoPedido;
    private float montoTotal;
    private Date fechaEntrega;
    private ClienteDTO cliente;
}
