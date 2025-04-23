package com.example.demo.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class LoginRequestDTO {
    private String correo;
    private String password;
}
