package com.example.demo.services;


import com.example.demo.models.dto.InsumoProduccionDTO;
import com.example.demo.models.dto.OrdenFinalizacionDTO;
import com.example.demo.models.dto.OrdenPedidoDTO;

import java.time.LocalDate;
import java.util.List;

public interface OrdenPedidoService {
    OrdenPedidoDTO crearOrden(OrdenPedidoDTO orden);
    List<OrdenPedidoDTO> obtenerTodas();
    OrdenPedidoDTO obtenerOrdenPorId(String id);
    void asignarOperador(String ordenId, String operadorId);
    void agregarInsumosProduccion(String ordenId, String produccionId, List<InsumoProduccionDTO> insumosDTO);
    void actualizarEstado(String id, String nuevoEstado);
    OrdenPedidoDTO actualizarOrdenPedido(String id, OrdenPedidoDTO dto);
    void eliminarOrdenPedido(String id);
    long contarOrdenesPorFechaEntrega(LocalDate fechaEntrega);
    OrdenPedidoDTO finalizarOrden(String idOrden, OrdenFinalizacionDTO dto);
}
