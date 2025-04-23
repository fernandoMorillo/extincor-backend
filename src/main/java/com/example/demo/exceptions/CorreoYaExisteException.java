package com.example.demo.exceptions;

public class CorreoYaExisteException extends RuntimeException {
    public CorreoYaExisteException(String message) {
        super(message);
    }
}
