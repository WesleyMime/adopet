package br.com.adopet.domain.controller.exception;

import br.com.adopet.domain.model.exception.EmailAlreadyRegisteredException;
import br.com.adopet.domain.model.exception.ExceptionDTO;
import br.com.adopet.domain.model.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST, "Parâmetro inválido.");
        return new ResponseEntity<>(exceptionDTO, exceptionDTO.status());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ConstraintViolationException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionDTO.status());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionDTO.status());
    }

    @ExceptionHandler(value = EmailAlreadyRegisteredException.class)
    public ResponseEntity<?> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionDTO.status());
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<?> handleUnexpectedException(Throwable e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Erro não esperado.");
        return new ResponseEntity<>(exceptionDTO, exceptionDTO.status());
    }

}
