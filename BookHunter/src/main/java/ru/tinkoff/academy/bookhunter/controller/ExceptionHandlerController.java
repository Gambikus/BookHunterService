package ru.tinkoff.academy.bookhunter.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tinkoff.academy.bookhunter.components.Error;
import ru.tinkoff.academy.bookhunter.exception.UserProfileNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler
    ResponseEntity<?> handleNotFoundException( UserProfileNotFoundException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage(), 404), HttpStatus.NOT_FOUND);
    }
}
