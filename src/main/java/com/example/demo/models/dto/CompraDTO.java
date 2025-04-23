package com.example.demo.models.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CompraDTO {
    private Long id;
    private String detalle;
    private String proveedor;
    private Date fechaCompra;
    private float monto;
    private String estado;
}
