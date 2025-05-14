package com.example.demo.services.impl;

import com.example.demo.mapper.OrdenPedidoMapper;
import com.example.demo.models.dto.DetallePedidoDTO;
import com.example.demo.models.dto.InsumoProduccionDTO;
import com.example.demo.models.entity.DetallePedido;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.models.entity.DetallePedidoEmbed;
import com.example.demo.models.entity.InsumoProduccionEmbed;
import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.services.OrdenPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenPedidoServiceImpl implements OrdenPedidoService {

    @Autowired
    private OrdenPedidoRepository repository;

    @Autowired
    private OrdenPedidoMapper mapper;
    @Autowired
    private OrdenPedidoService ordenPedidoService;
    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public OrdenPedidoDTO crearOrden(OrdenPedidoDTO dto) {
        OrdenPedido orden = mapper.toEntity(dto);

        orden = repository.save(orden);

        String numeroPedidoGenerado = "#Orden" + orden.getId().substring(orden.getId().length() - 6);
        orden.setNumeroPedido(numeroPedidoGenerado);

        orden = repository.save(orden);

        return mapper.toDTO(orden);
    }



    @Override
    public List<OrdenPedidoDTO> obtenerTodas() {
        return repository.findAll().stream().map(orden -> OrdenPedidoDTO.builder()
                .id(String.valueOf(orden.getId()))
                .numeroPedido(orden.getNumeroPedido())
                .estadoPedido(orden.getEstadoPedido())
                .fechaEntrega(orden.getFechaEntrega())
                .fechaPedido(orden.getFechaPedido())
                .montoTotal(orden.getMontoTotal())
                .clienteId(String.valueOf(orden.getClienteId()))
                .observacion(orden.getObservacion())
                .operarioId(String.valueOf(orden.getOperarioId()))
                .tipoServicio(orden.getTipoServicio())
                .detallePedidos(orden.getDetallePedidos() != null ? orden.getDetallePedidos().stream()
                        .map(d -> DetallePedidoDTO.builder()
                                .id(d.getId())
                                .cantidad(d.getCantidad())
                                .build()).collect(Collectors.toList()) : null)
                .insumosProduccion(orden.getInsumosProduccion() != null ? orden.getInsumosProduccion().stream()
                        .map(i -> InsumoProduccionDTO.builder()
                                .id(i.getId())
                                .cantidad(i.getCantidad())
                                .estado(i.getEstado())
                                .ordenPedidoId(String.valueOf(i.getOrdenPedidoId()))
                                .build()).collect(Collectors.toList()) : null)
                .build()).collect(Collectors.toList());
    }

    @Override
    public OrdenPedidoDTO obtenerOrdenPorId(String id) {
        return ordenPedidoRepository.findById(id)
                .map(orden -> {
                    OrdenPedidoDTO dto = mapper.toDTO(orden);

                    if (orden.getClienteId() != null) {
                        usuarioRepository.findById(orden.getClienteId())
                                .ifPresent(usuario -> dto.setClienteNombre(usuario.getNombre()));
                    }

                    if (orden.getOperarioId() != null) {
                        usuarioRepository.findById(orden.getOperarioId())
                                .ifPresent(usuario -> dto.setOperarioNombre(usuario.getNombre()));
                    }

                    return dto;
                })
                .orElse(null);
    }

}