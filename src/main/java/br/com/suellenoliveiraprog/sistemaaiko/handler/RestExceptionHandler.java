package br.com.suellenoliveiraprog.sistemaaiko.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.suellenoliveiraprog.sistemaaiko.model.erros.mensagemDeErro;
import br.com.suellenoliveiraprog.sistemaaiko.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception01){

        mensagemDeErro erro01 = new mensagemDeErro("Not Found", HttpStatus.NOT_FOUND.value(), exception01.getMessage());
        return new ResponseEntity<>(erro01, HttpStatus.NOT_FOUND);

    }
}
