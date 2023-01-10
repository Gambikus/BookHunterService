package ru.tinkoff.academy.bookhunter.dto;

import lombok.*;
import ru.tinkoff.academy.bookhunter.model.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    private String nick;

    private String name;

    private Integer age;

    private Gender gender;

    private Double latitude;

    private Double longitude;
}
