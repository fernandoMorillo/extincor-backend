package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.demo.models.entity.ExtintorPrediccion;
import com.example.demo.models.entity.PrediccionResultado;
import com.example.demo.services.impl.PrediccionExtintorService;

import java.util.List;

@RestController
@RequestMapping("/api/prediccion")
@CrossOrigin(origins = "*") // 🔓 Permite peticiones desde cualquier origen (útil para React)
public class PrediccionController {

    @Autowired
    private PrediccionExtintorService prediccionService;

    /**
     * 📤 Endpoint principal para realizar una predicción
     * Recibe los datos de un extintor y devuelve el tipo de error predicho
     */
    @PostMapping("/predecir")
    public ResponseEntity<?> predecir(@RequestBody ExtintorPrediccion datos) {
        try {
            PrediccionResultado resultado = prediccionService.predictAndSave(datos);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al realizar la predicción: " + e.getMessage());
        }
    }

    /**
     * 📋 Devuelve todas las predicciones realizadas hasta ahora
     */
    @GetMapping("/historial")
    public ResponseEntity<List<PrediccionResultado>> obtenerHistorial() {
        return ResponseEntity.ok(prediccionService.getPredicciones());
    }

    /**
     * 🧠 Endpoint de prueba para verificar que el modelo está cargado correctamente
     */
    @GetMapping("/estado")
    public ResponseEntity<String> estadoModelo() {
        return ResponseEntity.ok("✅ Servicio de predicción activo y modelo cargado correctamente.");
    }
}