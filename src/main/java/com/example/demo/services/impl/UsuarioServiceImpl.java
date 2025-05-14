package com.example.demo.services.impl;

import com.example.demo.models.dto.UsuarioDTO;
import com.example.demo.models.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO findById(String id) {
        return usuarioRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public List<UsuarioDTO> findByTipoUsuario(String tipoUsuario) {
        return usuarioRepository.findByTipoUsuario(tipoUsuario)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Usuario guardarUsuarioConRol(Usuario usuario, String rol) {
        usuario.setTipoUsuario(rol);
        return usuarioRepository.save(usuario);
    }


    @Override
    public UsuarioDTO save(UsuarioDTO dto) {
        Usuario usuario = toEntity(dto);
        Usuario saved = usuarioRepository.save(usuario);
        return toDTO(saved);
    }

    @Override
    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setTipoUsuario(entity.getTipoUsuario());
        dto.setIdSecuencial(entity.getIdSecuencial());
        dto.setCorreo(entity.getCorreo());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setNombre(entity.getNombre());
        dto.setPassword(entity.getPassword());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setEspecialidad(entity.getEspecialidad());
        dto.setEstado(entity.getEstado());
        dto.setVentanaId(entity.getVentanaId());
        dto.setTipoCliente(entity.getTipoCliente());

        dto.setOrdenesPedidos(entity.getOrdenesPedidos());
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        Usuario entity = new Usuario();
        entity.setId(dto.getId());
        entity.setTipoUsuario(dto.getTipoUsuario());
        entity.setIdSecuencial(dto.getIdSecuencial());
        entity.setCorreo(dto.getCorreo());
        entity.setFechaCreacion(dto.getFechaCreacion());
        entity.setNombre(dto.getNombre());
        entity.setPassword(dto.getPassword());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setEspecialidad(dto.getEspecialidad());
        entity.setEstado(dto.getEstado());
        entity.setVentanaId(dto.getVentanaId());
        entity.setTipoCliente(dto.getTipoCliente());

        entity.setOrdenesPedidos(dto.getOrdenesPedidos());
        return entity;
    }
}
