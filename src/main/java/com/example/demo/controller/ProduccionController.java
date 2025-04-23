package com.example.demo.controller;

import com.example.demo.models.dto.ProduccionDTO;
import com.example.demo.services.ProduccionService;
import com.example.demo.services.OperarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/producciones")
public class ProduccionController {

    @Autowired
    private ProduccionService produccionService;

    @Autowired
    private OperarioService operarioService; // Inyectar servicio de operarios

    @GetMapping
    public String listProducciones(Model model) {
        model.addAttribute("producciones", produccionService.findAll());
        model.addAttribute("produccion", new ProduccionDTO()); // Objeto para el formulario de creación
        model.addAttribute("operarios", operarioService.findAll()); // Cargar lista de operarios para el formulario
        return "producciones"; // Vista combinada
    }

    @PostMapping("/guardar")
    public String saveOrUpdateProduccion(@ModelAttribute ProduccionDTO produccionDTO) {
        produccionService.save(produccionDTO);
        return "redirect:/producciones";
    }

    @GetMapping("/editar/{id}")
    public String editProduccion(@PathVariable Long id, Model model) {
        model.addAttribute("producciones", produccionService.findAll());
        model.addAttribute("produccion", produccionService.findById(id)); // Objeto específico para edición
        model.addAttribute("operarios", operarioService.findAll()); // Asegura que operarios esté disponible al editar
        return "producciones";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteProduccion(@PathVariable Long id) {
        produccionService.deleteById(id);
        return "redirect:/producciones";
    }
}
