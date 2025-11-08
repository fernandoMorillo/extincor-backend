package com.example.demo.services.impl;

import com.example.demo.mapper.OrdenPedidoMapper;

import com.example.demo.models.dto.InsumoProduccionDTO;
import com.example.demo.models.dto.OrdenFinalizacionDTO;
import com.example.demo.models.entity.*;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.repository.*;
import com.example.demo.services.OrdenPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public OrdenPedidoDTO crearOrden(OrdenPedidoDTO dto) {
        LocalDate fecha = LocalDate.from(dto.getFechaPedido());
        List<OrdenPedidoDTO> resultList = new ArrayList<>();

        int cantidadSolicitada = dto.getCantidad();
        int cantidad = 1;

        // Validar capacidad diaria
        List<OrdenPedido> pedidosDelDia = ordenPedidoRepository.findByFechaPedido(fecha);
        int totalDelDia = pedidosDelDia.stream().mapToInt(OrdenPedido::getCantidad).sum();

        if ((totalDelDia + cantidadSolicitada) > MAX_EXTINTORES_POR_DIA) {
            throw new IllegalArgumentException("Capacidad máxima de extintores por día superada.");
        }

        if ("venta".equalsIgnoreCase(dto.getTipoServicio()) && cantidadSolicitada > 0) {
            cantidad = cantidadSolicitada;

            for (int i = 0; i < cantidad; i++) {
                OrdenPedido ordenPedido = mapper.toEntity(dto);
                ordenPedido.setCantidad(1);
                ordenPedido = ordenPedidoRepository.save(ordenPedido);

                String numeroPedidoGenerado = "#Orden"
                        + ordenPedido.getId().substring(ordenPedido.getId().length() - 6);
                ordenPedido.setNumeroPedido(numeroPedidoGenerado);

                ordenPedido = ordenPedidoRepository.save(ordenPedido);

                resultList.add(mapper.toDTO(ordenPedido));
            }

            return resultList.get(0);
        }

        OrdenPedido orden = mapper.toEntity(dto);
        orden = ordenPedidoRepository.save(orden);

        String numeroPedidoGenerado = "#Orden" + orden.getId().substring(orden.getId().length() - 6);
        orden.setNumeroPedido(numeroPedidoGenerado);
        orden = ordenPedidoRepository.save(orden);

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

        for (InsumoProduccionDTO dto : insumosDTO) {
            Insumo insumo = insumoRepository.findById(dto.getInsumoId())
                    .orElseThrow(() -> new RuntimeException("Insumo no encontrado: " + dto.getInsumoId()));

            if (insumo.getStock() < dto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el insumo: " + insumo.getNombre());
            }

            insumo.setStock(insumo.getStock() - dto.getCantidad());
            insumoRepository.save(insumo);
        }

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName(); // o getId si lo manejas así

        Usuario usuario = usuarioRepository.findByNombre(nombre);
        System.out.println("usuario: " + usuario);

        if (usuario.getTipoUsuario().equals("ADMINISTRADOR")) {
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
                                    .build())
                            .collect(Collectors.toList()) : null)
                    .insumosProduccion(orden.getInsumosProduccion() != null ? orden.getInsumosProduccion().stream()
                            .map(i -> InsumoProduccionEmbed.builder()
                                    .id(i.getId())
                                    .cantidad(i.getCantidad())
                                    .estado(i.getEstado())
                                    .ordenPedidoId(i.getOrdenPedidoId())
                                    .build())
                            .collect(Collectors.toList()) : null)
                    .build()).collect(Collectors.toList());
        } else {
            // El operador solo ve sus órdenes asignadas
            return ordenPedidoRepository.findByOperarioId(usuario.getId())
                    .stream().map(mapper::toDTO).collect(Collectors.toList());
        }
    }

    @Override
    public OrdenPedidoDTO obtenerOrdenPorId(String id) {
        return ordenPedidoRepository.findById(id)
                .map(orden -> {
                    OrdenPedidoDTO dto = mapper.toDTO(orden);

                    if (orden.getClienteId() != null) {
                        usuarioRepository.findById(orden.getClienteId())
                                .ifPresent(usuario -> dto.setClienteNombre(usuario.getNombre()));
                        usuarioRepository.findById(orden.getClienteId())
                                .ifPresent(usuario -> dto.setClienteEmail(usuario.getCorreo()));
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

    @Override
    public OrdenPedidoDTO finalizarOrden(String idOrden, OrdenFinalizacionDTO dto) {
        OrdenPedido orden = ordenPedidoRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        orden.setEstadoPedido("FINALIZADA");
        orden.setFechaFin(LocalDateTime.now());
        ordenPedidoRepository.save(orden);

        String produccionId = orden.getProduccionId();
        if (produccionId != null) {
            Produccion produccion = produccionRepository.findById(produccionId)
                    .orElseThrow(() -> new RuntimeException("Producción no encontrada para esta orden"));

            produccion.setEstado("FINALIZADA");
            produccion.setFechaFin(LocalDate.from(LocalDateTime.now()));
            produccionRepository.save(produccion);

            Producto producto = new Producto();
            producto.setFechaFabricacion(produccion.getFechaFin().atStartOfDay());
            producto.setNombre(produccion.getProductoNombre());
            producto.setEstado("DISPONIBLE");
            producto.setTipo("EXTINTOR");
            producto.setCodigo(UUID.randomUUID().toString());
            producto.setCapacidad(10.0);
            producto.setPrecio(0.0);
            producto.setDetallePedidos(new ArrayList<>());
            producto.setEnvases(new ArrayList<>());

            productoRepository.save(producto);
        }

        // ✅ Enviar correo al cliente
        String nombreCliente = dto.getNombreCliente();
        String correoCliente = dto.getCorreoCliente();

        if (correoCliente == null || correoCliente.isBlank()) {
            throw new IllegalArgumentException("Correo del cliente es inválido.");
        }

        String asunto = "Tu orden ha sido completada";
        String cuerpoHtml = generarContenidoCorreo(nombreCliente, orden.getNumeroPedido());

        emailServiceImpl.enviarCorreoHtml(correoCliente, asunto, cuerpoHtml);

        return mapper.toDTO(orden);
    }

    public String generarContenidoCorreo(String nombreCliente, String numeroPedido) {
        String plantilla = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <title>Orden Finalizada</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 30px;">
                    <div style="max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 10px;">
                        <h2 style="color: #2c3e50;">Hola, <span style="color: #2980b9;">{{nombreCliente}}</span></h2>
                        <p>Nos complace informarte que tu orden <strong>#{{numeroPedido}}</strong> ha sido <span style="color: green;">completada exitosamente</span>.</p>

                        <hr style="border: none; border-top: 1px solid #e0e0e0; margin: 20px 0;">

                        <p>Gracias por confiar en nuestro servicio. Si tienes alguna consulta, no dudes en contactarnos.</p>

                        <p style="margin-top: 30px;">Saludos cordiales,<br>
                        <strong>Equipo de Extintores S.A.</strong></p>
                    </div>
                </body>
                </html>
                """;

        return plantilla
                .replace("{{nombreCliente}}", nombreCliente)
                .replace("{{numeroPedido}}", numeroPedido);
    }

}