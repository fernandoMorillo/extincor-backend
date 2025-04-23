package com.example.demo.models.dto;

public class AuthRequest {
    private String correo;
    private String password;

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
