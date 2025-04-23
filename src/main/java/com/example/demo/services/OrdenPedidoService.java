package com.example.demo.services;

import com.example.demo.models.dto.OrdenPedidoDTO;
import java.util.Date;
import java.util.List;

public interface OrdenPedidoService {

    /**
     * Obtiene todas las órdenes de pedido.
     * @return Lista de objetos OrdenPedidoDTO.
     */
    List<OrdenPedidoDTO> findAll();

    /**
     * Busca una orden de pedido por su ID.
     * @param id ID de la orden.
     * @return Objeto OrdenPedidoDTO correspondiente.
     */
    OrdenPedidoDTO findById(Long id);

    /**
     * Guarda o actualiza una orden de pedido.
     * @param ordenPedidoDTO Objeto OrdenPedidoDTO con la información de la orden.
     * @return OrdenPedidoDTO actualizado.
     */
    OrdenPedidoDTO save(OrdenPedidoDTO ordenPedidoDTO);

    /**
     * Elimina una orden de pedido por su ID.
     * @param id ID de la orden.
     */
    void deleteById(Long id);

    /**
     * Genera el siguiente número de pedido en el formato personalizado.
     * Por ejemplo, "H0001", "H0002".
     * @return El siguiente número de pedido formateado.
     */
    String generateNextNumeroPedido();

    /**
     * Cambia el estado de una orden de pedido.
     * @param id ID de la orden.
     * @param nuevoEstado Nuevo estado de la orden (Activo, Inactivo, Pendiente).
     * @return OrdenPedidoDTO actualizado.
     */
    OrdenPedidoDTO cambiarEstado(Long id, String nuevoEstado);

    /**
     * Actualiza la fecha de entrega de una orden.
     * @param id ID de la orden.
     * @param nuevaFecha Nueva fecha de entrega.
     * @return OrdenPedidoDTO actualizado.
     */
    OrdenPedidoDTO actualizarFechaEntrega(Long id, Date nuevaFecha);
}
