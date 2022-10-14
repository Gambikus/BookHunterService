package ru.tinkoff.academy.bookhunter.components;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
    private String message;
    private int code;
}
