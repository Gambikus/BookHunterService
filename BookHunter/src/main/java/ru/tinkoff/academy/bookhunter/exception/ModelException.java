package ru.tinkoff.academy.bookhunter.exception;

public class ModelException extends RuntimeException{
    public ModelException() {
    }

    public ModelException(String message) {
        super(message);
    }
}
