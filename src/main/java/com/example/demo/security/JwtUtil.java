package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "EstaEsUnaClaveSecretaMuySeguraDe32Caracteres!";
    private static final long EXPIRATION_TIME = 86400000; // 1 día (24 horas)

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Clave segura

    // Generar token JWT
    public String generateToken(String correo) {
        return Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256) // Usa la clave segura
                .compact();
    }

    // Extraer correo del token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Validar el token
    public boolean validateToken(String token, String correo) {
        return correo.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    // Obtener Claims correctamente
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Usa la clave correcta
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Verificar si el token ha expirado
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
