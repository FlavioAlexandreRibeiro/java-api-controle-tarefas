package com.API_Controle_Tarefas.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String fieldName, String value) {
        super(String.format("%s duplicado: %s", fieldName, value));
    }
}
