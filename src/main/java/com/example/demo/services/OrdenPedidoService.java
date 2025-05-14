package com.example.demo.services;


import com.example.demo.models.dto.OrdenPedidoDTO;

import java.util.List;

public interface OrdenPedidoService {
    OrdenPedidoDTO crearOrden(OrdenPedidoDTO orden);
    List<OrdenPedidoDTO> obtenerTodas();
    OrdenPedidoDTO obtenerOrdenPorId(String id);
}
