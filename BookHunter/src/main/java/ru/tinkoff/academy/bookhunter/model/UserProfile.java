package ru.tinkoff.academy.bookhunter.model;

import lombok.*;
import ru.tinkoff.academy.bookhunter.model.enums.Gender;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class UserProfile {
    //@Id
    private UUID id = UUID.randomUUID();

    //@Column
    private String nick;

    //@Column
    private String name;

    //@Column
    private Integer age;

    //@Column
    private Gender gender;

    //@Column
    private Double latitude;

    //@Column
    private Double longitude;
}
