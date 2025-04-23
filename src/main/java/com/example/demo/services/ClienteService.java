package com.example.demo.services;

import com.example.demo.models.dto.ClienteDTO;
import java.util.List;

public interface ClienteService {
    List<ClienteDTO> findAll();
    ClienteDTO findById(Long id);
    ClienteDTO findByCorreo(String correo); // Método para buscar cliente por correo
    ClienteDTO save(ClienteDTO clienteDTO);
    void deleteById(Long id);
}
