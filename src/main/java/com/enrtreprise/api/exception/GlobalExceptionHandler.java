package com.enrtreprise.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- 400 BAD REQUEST : erreurs de validation
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiException> handleValidationException(BadRequestException ex) {
        ApiException error = new ApiException("Requête invalide", ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }

    // --- 404 NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiException> handleNotFound(ResourceNotFoundException ex) {
        ApiException error = new ApiException("Ressource introuvable", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // --- 409 CONFLICT
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiException> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        ApiException error = new ApiException("Conflit de ressource", ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // --- 500 INTERNAL SERVER ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleInternal(Exception ex) {

        ApiException error = new ApiException(
                "Erreur interne du serveur",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<ApiException> handleNoHandlerFound(org.springframework.web.servlet.NoHandlerFoundException ex) {
        ApiException error = new ApiException(
                "Ressource introuvable",
                "L’URL demandée est invalide : " + ex.getRequestURL(),
                HttpStatus.NOT_FOUND.value()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
