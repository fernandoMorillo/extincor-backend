package com.example.demo.controller;

import com.example.demo.models.entity.OperarioVentana;
import com.example.demo.services.OperarioVentanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ventanaoperario")
public class OperarioVentanaController {

    @Autowired
    private OperarioVentanaService operarioVentanaService;

    @GetMapping("/{id}")
    public String getVentanaById(@PathVariable Long id, Model model) {
        OperarioVentana ventana = operarioVentanaService.findById(id);
        if (ventana != null) {
            model.addAttribute("ventana", ventana);
            return "ventanaoperario";
        } else {
            return "errorPage"; // Muestra error si no se encuentra la ventana
        }
    }
}

