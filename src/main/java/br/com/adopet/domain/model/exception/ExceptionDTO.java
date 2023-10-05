package br.com.adopet.domain.model.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionDTO(LocalDateTime timestamp, HttpStatus status, String message) {

    public ExceptionDTO(HttpStatus status, String message) {
        this(LocalDateTime.now(), status, message);
    }
}
