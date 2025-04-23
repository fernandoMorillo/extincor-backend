package com.example.demo.services.impl;

import com.example.demo.models.dto.ClienteDTO;
import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.DetallePedidoRepository;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.services.ClienteService;
import com.example.demo.exceptions.CorreoYaExisteException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private   DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        return cliente != null ? convertToDTO(cliente) : null;
    }

    @Override
    @Transactional
    public ClienteDTO findByCorreo(String correo) {
        Cliente cliente = clienteRepository.findByCorreo(correo).orElse(null);
        return cliente != null ? convertToDTO(cliente) : null;
    }

    @Override
    @Transactional
    public ClienteDTO save(ClienteDTO clienteDTO) {
        // Verifica si el correo ya existe y no pertenece al cliente que se está actualizando
        clienteRepository.findByCorreo(clienteDTO.getCorreo()).ifPresent(existingCliente -> {
            if (!existingCliente.getId().equals(clienteDTO.getId())) {
                throw new CorreoYaExisteException("El correo ya está en uso.");
            }
        });
    
        Cliente cliente;
        if (clienteDTO.getId() != null) {
            // Recupera el cliente existente para actualizarlo
            cliente = clienteRepository.findById(clienteDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        } else {
            // Crea un nuevo cliente
            cliente = new Cliente();
        }
    
        // Actualiza los datos
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
    
        // Solo actualiza la contraseña si se proporciona una nueva
        if (clienteDTO.getPassword() != null && !clienteDTO.getPassword().isEmpty()) {
            cliente.setPassword(passwordEncoder.encode(clienteDTO.getPassword()));
        }
    
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDTO(savedCliente);
    }
    

    @Override
    @Transactional
    public void deleteById(Long clienteId) {
        List<OrdenPedido> ordenes = ordenPedidoRepository.findByClienteId(clienteId);
        for (OrdenPedido orden : ordenes) {
            detallePedidoRepository.deleteByOrdenPedidoId(orden.getId());
        }
        ordenPedidoRepository.deleteByClienteId(clienteId);
        clienteRepository.deleteById(clienteId);
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setCorreo(cliente.getCorreo());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    private Cliente convertToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        return cliente;
    }
}
