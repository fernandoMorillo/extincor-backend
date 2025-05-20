package com.example.demo.services.impl;

import com.example.demo.mapper.OrdenPedidoMapper;
import com.example.demo.models.dto.DetallePedidoDTO;
import com.example.demo.models.dto.InsumoProduccionDTO;
import com.example.demo.models.entity.*;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.repository.ProduccionRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.services.OrdenPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenPedidoServiceImpl implements OrdenPedidoService {

    private static final int MAX_EXTINTORES_POR_DIA = 100;

    @Autowired
    private OrdenPedidoRepository repository;

    @Autowired
    private OrdenPedidoMapper mapper;

    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProduccionRepository produccionRepository;

    @Override
    public OrdenPedidoDTO crearOrden(OrdenPedidoDTO dto) {
        LocalDate fecha = LocalDate.from(dto.getFechaPedido());
        int cantidadSolicitada = dto.getCantidad();

        List<OrdenPedido> pedidosDelDia = ordenPedidoRepository.findByFechaPedido(fecha);
        int totalDelDia = pedidosDelDia.stream().mapToInt(OrdenPedido::getCantidad).sum();

        if ((totalDelDia + cantidadSolicitada) > MAX_EXTINTORES_POR_DIA) {
            throw new IllegalArgumentException("Capacidad máxima de extintores por día superada.");
        }


        OrdenPedido orden = mapper.toEntity(dto);
        orden = repository.save(orden);

        String numeroPedidoGenerado = "#Orden" + orden.getId().substring(orden.getId().length() - 6);
        orden.setNumeroPedido(numeroPedidoGenerado);

        orden = repository.save(orden);

        return mapper.toDTO(orden);
    }

    @Override
    public void agregarInsumosProduccion(String ordenId, String produccionId, List<InsumoProduccionDTO> insumosDTO) {

        List<InsumoProduccionEmbed> insumos = insumosDTO.stream().map(dto -> {
            InsumoProduccionEmbed embed = new InsumoProduccionEmbed();
            embed.setInsumoId(dto.getInsumoId());
            embed.setCantidad(dto.getCantidad());
            embed.setOrdenPedidoId(dto.getOrdenPedidoId());
            embed.setEstado(dto.getEstado());
            embed.setProduccionId(dto.getProduccionId());
            return embed;
        }).collect(Collectors.toList());


        OrdenPedido orden = ordenPedidoRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden de pedido no encontrada: " + ordenId));
        orden.setInsumosProduccion(insumos);
        ordenPedidoRepository.save(orden);


        Produccion produccion = produccionRepository.findById(produccionId)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada: " + produccionId));
        produccion.setInsumosProduccion(insumos);
        produccionRepository.save(produccion);
    }

    @Override
    public void actualizarEstado(String id, String nuevoEstado) {
        OrdenPedido orden = ordenPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de pedido no encontrada con id: " + id));

        orden.setEstadoPedido(nuevoEstado);
        ordenPedidoRepository.save(orden);
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
                .tipoExtintor(orden.getTipoExtintor())
                .produccionId(orden.getProduccionId() != null ? String.valueOf(orden.getProduccionId()) : null)
                .detallePedidos(orden.getDetallePedidos() != null ? orden.getDetallePedidos().stream()
                        .map(d -> DetallePedidoEmbed.builder()
                                .id(d.getId())
                                .cantidad(d.getCantidad())
                                .build()).collect(Collectors.toList()) : null)
                .insumosProduccion(orden.getInsumosProduccion() != null ? orden.getInsumosProduccion().stream()
                        .map(i -> InsumoProduccionEmbed.builder()
                                .id(i.getId())
                                .cantidad(i.getCantidad())
                                .estado(i.getEstado())
                                .ordenPedidoId(i.getOrdenPedidoId())
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

    @Override
    public void asignarOperador(String ordenId, String operadorId) {
        OrdenPedido orden = ordenPedidoRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        Usuario operador = usuarioRepository.findById(operadorId)
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));

        if (!operador.getTipoUsuario().equalsIgnoreCase("OPERADOR")) {
            throw new RuntimeException("El usuario no es un operador");
        }

        orden.setOperarioId(operadorId);
        ordenPedidoRepository.save(orden);
    }

    @Override
    public OrdenPedidoDTO actualizarOrdenPedido(String id, OrdenPedidoDTO dto) {
        OrdenPedido orden = ordenPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));

        orden.setClienteId(dto.getClienteId());
        orden.setTipoExtintor(dto.getTipoExtintor());


        OrdenPedido ordenActualizada = ordenPedidoRepository.save(orden);
        return mapper.toDTO(ordenActualizada);

    }

    @Override
    public void eliminarOrdenPedido(String id) {
        if (!ordenPedidoRepository.existsById(id)) {
            throw new RuntimeException("Orden no encontrada con ID: " + id);
        }
        ordenPedidoRepository.deleteById(id);
    }

    @Override
    public long contarOrdenesPorFechaEntrega(LocalDate fechaEntrega) {
        ZonedDateTime startZdt = fechaEntrega.atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime endZdt = fechaEntrega.plusDays(1).atStartOfDay(ZoneId.of("UTC"));

        Date startDate = Date.from(startZdt.toInstant());
        Date endDate = Date.from(endZdt.toInstant());

        return ordenPedidoRepository.countByFechaEntrega(startDate, endDate);
    }


}