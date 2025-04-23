package com.example.demo.services.impl;

import com.example.demo.exceptions.CorreoYaExisteException;
import com.example.demo.models.dto.AdministradorDTO;
import com.example.demo.models.entity.Administrador;
import com.example.demo.repository.AdministradorRepository;
import com.example.demo.services.AdministradorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    @Transactional
    public List<AdministradorDTO> findAll() {
        return administradorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdministradorDTO findById(Long id) {
        Administrador administrador = administradorRepository.findById(id).orElse(null);
        return administrador != null ? convertToDTO(administrador) : null;
    }

    @Override
    @Transactional
    public AdministradorDTO save(AdministradorDTO administradorDTO) {
        // Verificar si el correo ya existe
        administradorRepository.findByCorreo(administradorDTO.getCorreo()).ifPresent(existingAdmin -> {
            if (!existingAdmin.getId().equals(administradorDTO.getId())) {
                throw new CorreoYaExisteException("El correo ya está en uso.");
            }
        });

        Administrador administrador;
        if (administradorDTO.getId() != null) {
            administrador = administradorRepository.findById(administradorDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado"));
        } else {
            administrador = new Administrador();
        }

        administrador.setNombre(administradorDTO.getNombre());
        administrador.setCorreo(administradorDTO.getCorreo());
        administrador.setTelefono(administradorDTO.getTelefono());

        if (administradorDTO.getPassword() != null && !administradorDTO.getPassword().isEmpty()) {
            administrador.setPassword(administradorDTO.getPassword());
        }

        administradorRepository.save(administrador);
        return convertToDTO(administrador);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        administradorRepository.deleteById(id);
    }

    private AdministradorDTO convertToDTO(Administrador administrador) {
        AdministradorDTO dto = new AdministradorDTO();
        dto.setId(administrador.getId());
        dto.setNombre(administrador.getNombre());
        dto.setCorreo(administrador.getCorreo());
        dto.setTelefono(administrador.getTelefono());
        dto.setFechaCreacion(administrador.getFechaCreacion());
        return dto;
    }

    private Administrador convertToEntity(AdministradorDTO administradorDTO) {
        Administrador administrador = new Administrador();
        administrador.setNombre(administradorDTO.getNombre());
        administrador.setCorreo(administradorDTO.getCorreo());
        administrador.setTelefono(administradorDTO.getTelefono());
        administrador.setFechaCreacion(administradorDTO.getFechaCreacion());
        administrador.setPassword(administradorDTO.getPassword());
        return administrador;
    }
}
