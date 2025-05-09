package com.example.demo.services.impl;

import com.example.demo.models.dto.*;
import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.OperarioIngreso;
import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.OperarioIngresoRepository;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.services.OrdenPedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenPedidoServiceImpl implements OrdenPedidoService {

    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private OperarioIngresoRepository operarioIngresoRepository;

    @Override
    @Transactional
    public List<OrdenPedidoDTO> findAll() {
        return ordenPedidoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public OrdenPedidoDTO actualizarFechaEntrega(Long id, Date nuevaFecha) {
        OrdenPedido ordenPedido = ordenPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // Actualiza la fecha de entrega
        ordenPedido.setFechaEntrega(nuevaFecha);

        // Guarda los cambios en la base de datos
        OrdenPedido ordenActualizada = ordenPedidoRepository.save(ordenPedido);

        // Devuelve el DTO actualizado
        return convertToDTO(ordenActualizada);
    }
    @Override
    @Transactional
    public OrdenPedidoDTO findById(Long id) {
        OrdenPedido ordenPedido = ordenPedidoRepository.findById(id).orElse(null);
        return ordenPedido != null ? convertToDTO(ordenPedido) : null;
    }

    @Override
    @Transactional
    public List<OrdenPedidoDTO> save(OrdenPedidoDTO ordenPedidoDTO) {
        List<OrdenPedidoDTO> resultList = new ArrayList<>();

        int cantidad = 1;
        if ("venta".equalsIgnoreCase(ordenPedidoDTO.getTipoServicio()) && ordenPedidoDTO.getCantidadExtintores() != null) {
            cantidad = ordenPedidoDTO.getCantidadExtintores();
        }

        for (int i = 0; i < cantidad; i++) {
            OrdenPedido ordenPedido = convertToEntity(ordenPedidoDTO);

            // Asignar número único e incrementable si es un nuevo pedido
            Long lastNumeroPedido = ordenPedidoRepository.findMaxNumeroPedido();
            ordenPedido.setNumeroPedido((lastNumeroPedido != null ? lastNumeroPedido : 0L) + 1);

            OrdenPedido saved = ordenPedidoRepository.save(ordenPedido);
            resultList.add(convertToDTO(saved));
        }

        return resultList;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ordenPedidoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public String generateNextNumeroPedido() {
        Long lastNumeroPedido = ordenPedidoRepository.findMaxNumeroPedido();
        Long nextNumero = (lastNumeroPedido != null ? lastNumeroPedido : 0L) + 1;
        return String.format("H%04d", nextNumero);
    }


    @Transactional
    public OrdenPedidoDTO cambiarEstado(Long id, EstadoPedidoRequestDTO request) {

        OrdenPedido orden = ordenPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        orden.setEstadoPedido(request.getEstadoPedido());
        OrdenPedido ordenActualizada = ordenPedidoRepository.save(orden);
        return convertToDTO(ordenActualizada);
    }

    @Override
    @Transactional
    public void asignarOperario(Long ordenId, Long operarioId) {
        OrdenPedido orden = ordenPedidoRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        OperarioIngreso operario = operarioIngresoRepository.findById(operarioId)
                .orElseThrow(() -> new RuntimeException("Operario no encontrado"));
        orden.setOperario(operario);
        ordenPedidoRepository.save(orden);
    }

    public List<OrdenPedido> obtenerOrdenesPorOperario(Long operarioId) {
        return ordenPedidoRepository.findByOperarioId(operarioId);
    }




    private OrdenPedidoDTO convertToDTO(OrdenPedido ordenPedido) {
        OrdenPedidoDTO dto = new OrdenPedidoDTO();
        dto.setId(ordenPedido.getId());

        // Convertir numeroPedido a String con formato "H0001"
        dto.setNumeroPedido(String.format("H%04d", ordenPedido.getNumeroPedido()));

        dto.setFechaPedido(ordenPedido.getFechaPedido());
        dto.setEstadoPedido(ordenPedido.getEstadoPedido());
        dto.setMontoTotal(ordenPedido.getMontoTotal());
        dto.setFechaEntrega(ordenPedido.getFechaEntrega());
        dto.setObservacion(ordenPedido.getObservacion());
        dto.setTipoServicio(ordenPedido.getTipoServicio());


        // Mapear cliente si existe
        if (ordenPedido.getCliente() != null) {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(ordenPedido.getCliente().getId());
            clienteDTO.setNombre(ordenPedido.getCliente().getNombre());
            clienteDTO.setCorreo(ordenPedido.getCliente().getCorreo());
            dto.setCliente(clienteDTO);
        }

        if (ordenPedido.getOperario() != null)  {
            OperarioIngreso operario = ordenPedido.getOperario();
            OperarioIngresoDto operarioIngresoDto = new OperarioIngresoDto();
            operarioIngresoDto.setId(operario.getId());
            operarioIngresoDto.setNombre(operario.getNombre());
            operarioIngresoDto.setCorreo(operario.getCorreo());
            operarioIngresoDto.setDireccion(operario.getDireccion());
            operarioIngresoDto.setTelefono(operario.getTelefono());
            operarioIngresoDto.setEspecialidad(operario.getEspecialidad());
            operarioIngresoDto.setEstado(operario.getEstado());

            // Convertir producciones a DTO
            if (operario.getProducciones() != null) {
                List<ProduccionDTO> produccionesDTO = operario.getProducciones()
                        .stream()
                        .map(prod -> {
                            ProduccionDTO dtoProd = new ProduccionDTO();
                            dtoProd.setId(prod.getId());
                            dtoProd.setCodigoProduccion(prod.getCodigoProduccion());
                            dtoProd.setFechaInicio(prod.getFechaInicio());
                            dtoProd.setFechaFin(prod.getFechaFin());
                            dtoProd.setCantidad_producida(prod.getCantidad_producida());
                            dtoProd.setProducto_nombre(prod.getProducto_nombre());
                            dtoProd.setEstado(prod.getEstado());
                            return dtoProd;
                        })
                        .collect(Collectors.toList());
                operarioIngresoDto.setProducciones(produccionesDTO);
            }

            dto.setOperario(operarioIngresoDto);

        }


        return dto;
    }

    private OrdenPedido convertToEntity(OrdenPedidoDTO dto) {
        OrdenPedido ordenPedido = new OrdenPedido();
        ordenPedido.setId(dto.getId());

        // Convertir numeroPedido de String a Long si tiene el prefijo "H"
        if (dto.getNumeroPedido() != null && dto.getNumeroPedido().startsWith("H")) {
            ordenPedido.setNumeroPedido(Long.parseLong(dto.getNumeroPedido().substring(1)));
        }

        ordenPedido.setFechaEntrega(ajustarHoraFinDelDia(dto.getFechaEntrega()));
        ordenPedido.setEstadoPedido(dto.getEstadoPedido());
        ordenPedido.setMontoTotal(dto.getMontoTotal());
        ordenPedido.setFechaEntrega(ajustarHoraFinDelDia(dto.getFechaEntrega()));
        ordenPedido.setObservacion(dto.getObservacion());
        ordenPedido.setTipoServicio(dto.getTipoServicio());


        // Mapear cliente si existe
        if (dto.getCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getCliente().getId()).orElse(null);
            ordenPedido.setCliente(cliente);
        }
        return ordenPedido;
    }

    private Date ajustarHoraFinDelDia(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}