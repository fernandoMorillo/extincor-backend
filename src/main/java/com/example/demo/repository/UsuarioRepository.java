package com.example.demo.repository;

import com.example.demo.models.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);


    List<Usuario> findByTipoUsuario(String tipoUsuario);

    Usuario findByNombre(String nombre);
}

