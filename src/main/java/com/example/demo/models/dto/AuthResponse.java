package com.example.demo.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;

    @JsonProperty("usuario")
    private UserResponse userResponse;

    public AuthResponse( String token, String correo, String roles, String nombre, String id ) {
        this.token = token;
        this.userResponse = new UserResponse(correo, roles, nombre, id);
    }

    public String getToken() {
        return token;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }




    public static class UserResponse {
        private String correo;
        private String roles;
        private String nombre;
        private String id;

        public UserResponse(String correo, String roles, String nombre, String id) {
            this.correo = correo;
            this.roles = roles;
            this.nombre = nombre;
            this.id = id;
        }

        public String getCorreo() {
            return correo;
        }

        public String getRoles() {
            return roles;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
