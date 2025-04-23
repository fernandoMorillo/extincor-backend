package com.example.demo.controller;

import com.example.demo.models.dto.ProductoDTO;
import com.example.demo.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listProductos(Model model) {
        model.addAttribute("productos", productoService.findAll());
        model.addAttribute("producto", new ProductoDTO()); // Objeto para el formulario de creación
        return "productos"; // Vista única
    }

    @PostMapping("/guardar")
    public String saveOrUpdateProducto(@ModelAttribute ProductoDTO productoDTO) {
        productoService.save(productoDTO);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editProducto(@PathVariable Long id, Model model) {
        model.addAttribute("productos", productoService.findAll());
        model.addAttribute("producto", productoService.findById(id)); // Objeto específico para edición
        return "productos";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return "redirect:/productos";
    }
}
