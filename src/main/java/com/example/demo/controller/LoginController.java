package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import com.example.demo.services.OperarioService; // Asegúrate de importar el servicio
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class LoginController {


    @Autowired
    private OperarioService operarioService; // Inyección del servicio

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos. Verifica tus datos e intenta nuevamente.");
        }
        return "login";
    }

    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index"; // Redirige la raíz a /index
    }

    @GetMapping("/index")
    public String index() {
        return "index"; // Muestra la página principal (index)
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("username", username);

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMINISTRADOR")) {
            return "redirect:/insumos";
        } else if (roles.contains("ROLE_CLIENTE")) {
            return "redirect:/ventanas";
        } else if (roles.contains("ROLE_OPERARIO")) {
            // Obtener el ID del operario a partir del correo (nombre de usuario)
            Long operarioId = operarioService.findIdByCorreo(username);
            if (operarioId != null) {
                return "redirect:/operarios/ventanaoperario?id=" + operarioId;
            } else {
                return "redirect:/error"; // Redirige a una página de error si no se encuentra el operario
            }
        } else {
            return "redirect:/403";
        }
    }
}