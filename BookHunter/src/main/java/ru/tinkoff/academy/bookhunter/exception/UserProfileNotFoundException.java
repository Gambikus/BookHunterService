package ru.tinkoff.academy.bookhunter.exception;

public class UserProfileNotFoundException extends ModelException{
    public UserProfileNotFoundException(String message) {
        super("User profile with " + message + " not found");
    }
}
