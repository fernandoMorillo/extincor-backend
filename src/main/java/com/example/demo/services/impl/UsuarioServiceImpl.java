package com.example.demo.services.impl;

import com.example.demo.exceptions.CorreoYaExisteException;
import com.example.demo.models.dto.UsuarioDTO;
import com.example.demo.models.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.services.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return usuario != null ? convertToDTO(usuario) : null;
    }

    @Override
    @Transactional
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        // Verifica si el correo ya existe
        if (usuarioRepository.findByCorreo(usuarioDTO.getCorreo()).isPresent()) {
            throw new CorreoYaExisteException("El correo ya está en uso.");
        }

        Usuario usuario = convertToEntity(usuarioDTO);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return convertToDTO(savedUsuario);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        return dto;
    }

    private Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setFechaCreacion(usuarioDTO.getFechaCreacion());
        return usuario;
    }
}
