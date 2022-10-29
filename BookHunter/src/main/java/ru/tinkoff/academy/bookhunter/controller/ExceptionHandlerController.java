package ru.tinkoff.academy.bookhunter.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.academy.bookhunter.components.Error;
import ru.tinkoff.academy.bookhunter.exception.UserProfileNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController { // extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(UserProfileNotFoundException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage(), 404), HttpStatus.NOT_FOUND);
    }
}
