package ru.tinkoff.academy.bookhunter.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.tinkoff.academy.bookhunter.components.WebApplicationException;
import ru.tinkoff.academy.bookhunter.exception.UserProfileNotFoundException;

@ControllerAdvice
public class ExceptionHandler { // extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<WebApplicationException> handleNotFoundException(UserProfileNotFoundException exception) {
        return new ResponseEntity<>(new WebApplicationException(exception.getMessage(), 404), HttpStatus.NOT_FOUND);
    }
}
