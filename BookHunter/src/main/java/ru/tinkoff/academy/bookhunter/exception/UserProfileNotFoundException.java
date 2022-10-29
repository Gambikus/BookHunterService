package ru.tinkoff.academy.bookhunter.exception;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException(String message) {
        super("User profile with " + message + " not found");
    }
}
