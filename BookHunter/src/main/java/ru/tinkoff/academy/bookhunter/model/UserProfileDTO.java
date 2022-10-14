package ru.tinkoff.academy.bookhunter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tinkoff.academy.bookhunter.model.enums.Gender;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private String nick;

    private String name;

    private Integer age;

    private Gender gender;

    private String location;
}
