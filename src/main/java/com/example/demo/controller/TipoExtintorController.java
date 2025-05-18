package com.example.demo.controller;

import com.example.demo.models.dto.TipoExtintorDTO;
import com.example.demo.services.TipoExtintorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-extintor")
public class TipoExtintorController {

    @Autowired
    private TipoExtintorService tipoExtintorService;

    @GetMapping
    public List<TipoExtintorDTO> obtenerTiposExtintor() {
        return tipoExtintorService.obtenerTiposExtintor();
    }
}
