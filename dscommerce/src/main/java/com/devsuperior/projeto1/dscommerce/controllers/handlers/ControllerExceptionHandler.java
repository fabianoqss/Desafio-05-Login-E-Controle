package com.devsuperior.projeto1.dscommerce.controllers.handlers;

import com.devsuperior.projeto1.dscommerce.dto.CustomError;
import com.devsuperior.projeto1.dscommerce.dto.ValidationError;
import com.devsuperior.projeto1.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.projeto1.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;


// Meus Handlers , irão ser minha Resposta Customizada;
//Em uma classe com a annotation @ControllerAdvice, podemos definir tratamentos globais para
//exceções específicas, sem precisar ficar colocando try-catch em várias partes do código.
@ControllerAdvice
public class ControllerExceptionHandler {


        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
            return ResponseEntity.status(status).body(err);
        }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValodation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados Inválidos", request.getRequestURI());
        for(FieldError f :e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }
}
