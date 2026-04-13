package fr.clelia.avis.infrastructure.web.controller;

import fr.clelia.avis.domain.exception.EditeurDejaExistantException;
import fr.clelia.avis.domain.exception.EditeurNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EditeurNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {}

    @ExceptionHandler(EditeurDejaExistantException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleConflict() {}
}