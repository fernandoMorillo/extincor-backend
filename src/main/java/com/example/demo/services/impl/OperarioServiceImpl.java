package com.example.demo.services.impl;

import com.example.demo.exceptions.CorreoYaExisteException;
import com.example.demo.models.dto.OperarioIngresoDto;
import com.example.demo.models.dto.OrdenPedidoDTO;
import com.example.demo.models.entity.OperarioIngreso;
import com.example.demo.models.entity.OrdenPedido;
import com.example.demo.repository.OperarioIngresoRepository;
import com.example.demo.repository.OrdenPedidoRepository;
import com.example.demo.services.OperarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importamos el PasswordEncoder
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperarioServiceImpl implements OperarioService {

    @Autowired
    private OperarioIngresoRepository operarioRepository;

    @Autowired
    private OrdenPedidoRepository ordenPedidoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el encoder para las contraseñas

    @Override
    @Transactional
    public List<OperarioIngresoDto> findAll() {
        return operarioRepository.findAll()
                .stream()
                .map(operario -> {
                    OperarioIngresoDto dto = new OperarioIngresoDto();
                    dto.setId(operario.getId());
                    dto.setNombre(operario.getNombre());
                    dto.setDireccion(operario.getDireccion());
                    dto.setTelefono(operario.getTelefono());
                    dto.setCorreo(operario.getCorreo());
                    dto.setEspecialidad(operario.getEspecialidad());

                    // ✅ Obtener las órdenes asignadas
                    List<OrdenPedido> ordenes = ordenPedidoRepository.findByOperarioId(operario.getId());

                    // Convertir a DTO
                    List<OrdenPedidoDTO> ordenesDTO = ordenes.stream()
                            .map(this::convertToOrdenDTO) // asegúrate de tener este método
                            .collect(Collectors.toList());

                    dto.setOrdenes(ordenesDTO);

                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public Long findIdByCorreo(String correo) {
        return operarioRepository.findByCorreo(correo)
                .map(OperarioIngreso::getId)
                .orElse(null);
    }




    @Override
    @Transactional
    public OperarioIngresoDto findById(Long id) {
        OperarioIngreso operario = operarioRepository.findById(id).orElse(null);
        return operario != null ? convertToDTO(operario) : null;
    }

    @Override
    @Transactional
    public OperarioIngresoDto save(OperarioIngresoDto operarioDTO) {
        operarioRepository.findByCorreo(operarioDTO.getCorreo()).ifPresent(existing -> {
            if (!existing.getId().equals(operarioDTO.getId())) {
                throw new CorreoYaExisteException("El correo ya está en uso.");
            }
        });
    
        OperarioIngreso operario;
        if (operarioDTO.getId() != null) {
            operario = operarioRepository.findById(operarioDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Operario no encontrado"));
        } else {
            operario = new OperarioIngreso();
        }
    
        operario.setNombre(operarioDTO.getNombre());
        operario.setCorreo(operarioDTO.getCorreo());
        operario.setDireccion(operarioDTO.getDireccion());
        operario.setTelefono(operarioDTO.getTelefono());
        operario.setEspecialidad(operarioDTO.getEspecialidad());
    
        if (operarioDTO.getPassword() != null && !operarioDTO.getPassword().isEmpty()) {
            operario.setPassword(passwordEncoder.encode(operarioDTO.getPassword()));
        }
    
        OperarioIngreso savedOperario = operarioRepository.save(operario);
        return convertToDTO(savedOperario);
    }
    


    @Override
    @Transactional
    public void deleteById(Long id) {
        operarioRepository.deleteById(id);
    }

    private OperarioIngresoDto convertToDTO(OperarioIngreso operario) {
        OperarioIngresoDto dto = new OperarioIngresoDto();
        dto.setId(operario.getId());
        dto.setNombre(operario.getNombre());
        dto.setCorreo(operario.getCorreo());
        dto.setDireccion(operario.getDireccion());
        dto.setTelefono(operario.getTelefono());
        dto.setEspecialidad(operario.getEspecialidad());
        dto.setEstado(operario.getEstado());
        // Nota: No incluimos la contraseña en el DTO por motivos de seguridad
        return dto;
    }

    private OperarioIngreso convertToEntity(OperarioIngresoDto operarioDTO) {
        OperarioIngreso operario = new OperarioIngreso();
        operario.setId(operarioDTO.getId());
        operario.setNombre(operarioDTO.getNombre());
        operario.setCorreo(operarioDTO.getCorreo());
        operario.setDireccion(operarioDTO.getDireccion());
        operario.setTelefono(operarioDTO.getTelefono());
        operario.setEspecialidad(operarioDTO.getEspecialidad());
        operario.setEstado(operarioDTO.getEstado());
        return operario;
    }

    private OrdenPedidoDTO convertToOrdenDTO(OrdenPedido orden) {
        OrdenPedidoDTO dto = new OrdenPedidoDTO();
        dto.setId(orden.getId());
        dto.setNumeroPedido(String.format("H%04d", orden.getNumeroPedido()));
        dto.setEstadoPedido(orden.getEstadoPedido());
        dto.setMontoTotal(orden.getMontoTotal());
        dto.setFechaPedido(orden.getFechaPedido());
        dto.setFechaEntrega(orden.getFechaEntrega());
        dto.setObservacion(orden.getObservacion());

        return dto;
    }

}
