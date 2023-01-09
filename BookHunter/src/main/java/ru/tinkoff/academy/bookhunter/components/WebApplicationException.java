package ru.tinkoff.academy.bookhunter.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebApplicationException {
    private String message;
    private int code;
}
