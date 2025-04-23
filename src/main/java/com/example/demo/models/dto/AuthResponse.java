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

    public AuthResponse( String token, String correo, String roles ) {
        this.token = token;
        this.userResponse = new UserResponse(correo, roles);
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

        public UserResponse(String correo, String roles) {
            this.correo = correo;
            this.roles = roles;
        }

        public String getCorreo() {
            return correo;
        }

        public String getRoles() {
            return roles;
        }
    }
}
