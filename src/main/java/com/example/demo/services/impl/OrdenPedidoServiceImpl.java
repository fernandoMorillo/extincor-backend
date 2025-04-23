package com.example.demo.services.impl;

import com.example.demo.models.dto.ClienteDTO;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.services.OrdenPedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenPedidoServiceImpl implements OrdenPedidoService {

    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

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
    public OrdenPedidoDTO save(OrdenPedidoDTO ordenPedidoDTO) {
        OrdenPedido ordenPedido = convertToEntity(ordenPedidoDTO);

        // Asignar número único e incrementable si es un nuevo pedido
        if (ordenPedido.getId() == null) {
            Long lastNumeroPedido = ordenPedidoRepository.findMaxNumeroPedido();
            ordenPedido.setNumeroPedido((lastNumeroPedido != null ? lastNumeroPedido : 0L) + 1);
        }

        OrdenPedido savedOrdenPedido = ordenPedidoRepository.save(ordenPedido);
        return convertToDTO(savedOrdenPedido);
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

    @Override
    @Transactional
    public OrdenPedidoDTO cambiarEstado(Long id, String nuevoEstado) {
        OrdenPedido orden = ordenPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        orden.setEstadoPedido(nuevoEstado);
        OrdenPedido ordenActualizada = ordenPedidoRepository.save(orden);
        return convertToDTO(ordenActualizada);
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

        // Mapear cliente si existe
        if (ordenPedido.getCliente() != null) {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(ordenPedido.getCliente().getId());
            clienteDTO.setNombre(ordenPedido.getCliente().getNombre());
            dto.setCliente(clienteDTO);
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

        ordenPedido.setFechaPedido(dto.getFechaPedido());
        ordenPedido.setEstadoPedido(dto.getEstadoPedido());
        ordenPedido.setMontoTotal(dto.getMontoTotal());
        ordenPedido.setFechaEntrega(dto.getFechaEntrega());

        // Mapear cliente si existe
        if (dto.getCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getCliente().getId()).orElse(null);
            ordenPedido.setCliente(cliente);
        }
        return ordenPedido;
    }
}
