package com.example.demo.services.impl;

import com.example.demo.models.dto.*;
import com.example.demo.models.entity.DetallePedido;
import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.models.entity.Produccion;
import com.example.demo.models.entity.Producto;
import com.example.demo.repository.DetallePedidoRepository;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.repository.ProduccionRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.services.DetallePedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProduccionRepository produccionRepository; // Agregamos el repositorio de Produccion

    @Override
    @Transactional
    public List<DetallePedidoDTO> findAll() {
        return detallePedidoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DetallePedidoDTO findById(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id).orElse(null);
        return detallePedido != null ? convertToDTO(detallePedido) : null;
    }

    @Override
    @Transactional
    public DetallePedidoDTO save(DetallePedidoDTO detallePedidoDTO) {
        DetallePedido detallePedido = convertToEntity(detallePedidoDTO);
        DetallePedido savedDetallePedido = detallePedidoRepository.save(detallePedido);
        return convertToDTO(savedDetallePedido);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        detallePedidoRepository.deleteById(id);
    }

    private DetallePedidoDTO convertToDTO(DetallePedido detallePedido) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(detallePedido.getId());
        dto.setCantidad(detallePedido.getCantidad());

        // Producto
        if (detallePedido.getProducto() != null) {
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setId(detallePedido.getProducto().getId());
            productoDTO.setCodigo(detallePedido.getProducto().getCodigo());
            productoDTO.setNombre(detallePedido.getProducto().getNombre());
            dto.setProducto(productoDTO);
        }

        // Orden de Pedido
        if (detallePedido.getOrdenpedido() != null) {
            OrdenPedidoDTO ordenPedidoDTO = new OrdenPedidoDTO();
            ordenPedidoDTO.setId(detallePedido.getOrdenpedido().getId());
            ordenPedidoDTO.setNumeroPedido(String.valueOf(detallePedido.getOrdenpedido().getNumeroPedido()));
            ordenPedidoDTO.setFechaPedido(detallePedido.getOrdenpedido().getFechaPedido());

            // Cliente dentro de la orden
            if (detallePedido.getOrdenpedido().getCliente() != null) {
                ClienteDTO clienteDTO = new ClienteDTO();
                clienteDTO.setId(detallePedido.getOrdenpedido().getCliente().getId());
                clienteDTO.setNombre(detallePedido.getOrdenpedido().getCliente().getNombre());
                clienteDTO.setDireccion(detallePedido.getOrdenpedido().getCliente().getDireccion());
                clienteDTO.setTelefono(detallePedido.getOrdenpedido().getCliente().getTelefono());
                ordenPedidoDTO.setCliente(clienteDTO);
            }

            dto.setOrdenpedido(ordenPedidoDTO);
        }

        // Producción
        if (detallePedido.getProduccion() != null) {
            ProduccionDTO produccionDTO = new ProduccionDTO();
            produccionDTO.setId(detallePedido.getProduccion().getId());
            produccionDTO.setCodigoProduccion(detallePedido.getProduccion().getCodigoProduccion());
            dto.setProduccion(produccionDTO);
        }

        return dto;
    }

    private DetallePedido convertToEntity(DetallePedidoDTO detallePedidoDTO) {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setCantidad(detallePedidoDTO.getCantidad());

        // Asociar la OrdenPedido si existe
        if (detallePedidoDTO.getOrdenpedido() != null && detallePedidoDTO.getOrdenpedido().getId() != null) {
            OrdenPedido ordenPedido = ordenPedidoRepository.findById(detallePedidoDTO.getOrdenpedido().getId()).orElse(null);
            detallePedido.setOrdenpedido(ordenPedido);
        } else {
            detallePedido.setOrdenpedido(null);
        }

        // Asociar el Producto si existe
        if (detallePedidoDTO.getProducto() != null && detallePedidoDTO.getProducto().getId() != null) {
            Producto producto = productoRepository.findById(detallePedidoDTO.getProducto().getId()).orElse(null);
            detallePedido.setProducto(producto);
        } else {
            detallePedido.setProducto(null);
        }

        // Asociar la Produccion si existe
        if (detallePedidoDTO.getProduccion() != null && detallePedidoDTO.getProduccion().getId() != null) {
            Produccion produccion = produccionRepository.findById(detallePedidoDTO.getProduccion().getId()).orElse(null);
            detallePedido.setProduccion(produccion);
        } else {
            detallePedido.setProduccion(null);
        }

        return detallePedido;
    }
}
