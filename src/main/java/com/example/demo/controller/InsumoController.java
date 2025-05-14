package com.example.demo.controller;

import com.example.demo.models.dto.InsumoDTO;
import com.example.demo.models.entity.Insumo;
import com.example.demo.models.enums.TipoExtintor;
import com.example.demo.services.InsumoService;
import com.example.demo.services.impl.InsumoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    @Autowired
    private InsumoService insumoService;
    @Autowired
    private InsumoServiceImpl insumoServiceImpl;

    @GetMapping
    public List<InsumoDTO> findAll() {
        return insumoService.findAll();
    }

    @GetMapping("/{id}")
    public InsumoDTO findById(@PathVariable String id) {
        return insumoService.findById(id);
    }

    @PostMapping
    public InsumoDTO save(@RequestBody InsumoDTO dto) {
        return insumoService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        insumoService.deleteById(id);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Insumo> obtenerInsumosPorTipo(@PathVariable String tipo) {
        try {
            TipoExtintor tipoExtintor = TipoExtintor.valueOf(tipo.toUpperCase());
            return insumoServiceImpl.obtenerPorTipoExtintor(tipoExtintor);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de extintor no válido. Valores permitidos: ABC, AGUA, CO2, TIPO_K");
        }
    }
}
