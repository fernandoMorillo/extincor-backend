package com.example.demo.repository;

import com.example.demo.models.entity.Insumo;
import com.example.demo.models.enums.TipoExtintor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InsumoRepository extends MongoRepository<Insumo, String> {

    List<Insumo> findByTiposExtintorContaining(TipoExtintor tipoExtintor);
}
