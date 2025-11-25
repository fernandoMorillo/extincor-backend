package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Lista de rutas públicas que NO requieren autenticación
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/auth/",
            "/api/public/",
            "/api/prediccion/",
            "/api/ai/",
            "/api/test/",
            "/error");

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        log.debug("🔍 Procesando request: {} {}", request.getMethod(), requestPath);

        // ✅ IMPORTANTE: Si es una ruta pública, continuar sin validar token
        if (isPublicPath(requestPath)) {
            log.debug("🟢 Ruta pública detectada: {}", requestPath);
            chain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        // Si no hay header Authorization, continuar (dejará que Spring Security maneje
        // la autorización)
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.debug("⚠️ No se encontró header Authorization válido en: {}", requestPath);
            chain.doFilter(request, response);
            return;
        }

        try {
            // Extraer token sin "Bearer "
            String token = authorizationHeader.substring(7).trim();

            // ✅ VALIDACIÓN 1: Verificar que el token no esté vacío
            if (token.isEmpty()) {
                log.warn("⚠️ Token JWT vacío en: {}", requestPath);
                chain.doFilter(request, response);
                return;
            }

            // ✅ VALIDACIÓN 2: Verificar formato del token (debe tener 3 partes)
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                log.warn("⚠️ Token JWT mal formado (tiene {} partes en lugar de 3) en: {}",
                        parts.length, requestPath);
                chain.doFilter(request, response);
                return;
            }

            log.debug("📥 Token recibido: {}...", token.substring(0, Math.min(20, token.length())));

            // Extraer email del token
            String email = jwtUtil.extractEmail(token);
            log.debug("📧 Email extraído: {}", email);

            // Si hay email y no hay autenticación previa
            if (email != null && !email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {

                try {
                    // Cargar detalles del usuario
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    // Validar el token
                    if (jwtUtil.validateToken(token, email)) {
                        // Crear token de autenticación
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                        // Agregar detalles adicionales
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Establecer autenticación en el contexto de seguridad
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        log.debug("✅ Usuario autenticado correctamente: {} con roles: {}",
                                email, userDetails.getAuthorities());
                    } else {
                        log.warn("⚠️ Token inválido para el usuario: {}", email);
                    }

                } catch (Exception e) {
                    log.error("❌ Error al cargar o validar usuario '{}': {}", email, e.getMessage());
                }
            }

        } catch (io.jsonwebtoken.MalformedJwtException e) {
            log.error("❌ Token JWT mal formado en {}: {}", requestPath, e.getMessage());
            // NO lanzar excepción, solo continuar sin autenticación

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.error("❌ Token JWT expirado en {}: {}", requestPath, e.getMessage());

        } catch (io.jsonwebtoken.SignatureException e) {
            log.error("❌ Firma del token inválida en {}: {}", requestPath, e.getMessage());

        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            log.error("❌ Token JWT no soportado en {}: {}", requestPath, e.getMessage());

        } catch (IllegalArgumentException e) {
            log.error("❌ Token JWT vacío o nulo en {}: {}", requestPath, e.getMessage());

        } catch (StringIndexOutOfBoundsException e) {
            log.error("❌ Error al extraer token (formato incorrecto) en {}: {}", requestPath, e.getMessage());

        } catch (Exception e) {
            log.error("❌ Error inesperado al procesar token en {}: {}", requestPath, e.getMessage());
        }

        // ✅ SIEMPRE continuar con la cadena de filtros (no romper el flujo)
        chain.doFilter(request, response);
    }

    /**
     * Verifica si la ruta es pública (no requiere autenticación)
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    /**
     * Este método permite omitir completamente el filtro para ciertas rutas
     * Esto mejora el rendimiento ya que ni siquiera procesa el header Authorization
     */
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();

        // Omitir el filtro completamente para rutas públicas
        boolean shouldSkip = isPublicPath(path);

        if (shouldSkip) {
            log.debug("🔓 Filtro JWT omitido completamente para: {}", path);
        }

        return shouldSkip;
    }
}