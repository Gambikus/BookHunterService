package ru.tinkoff.academy.bookhunter.DTO;

import lombok.*;
import ru.tinkoff.academy.bookhunter.model.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private String nick;

    private String name;

    private Integer age;

    private Gender gender;

    private String location;
}
