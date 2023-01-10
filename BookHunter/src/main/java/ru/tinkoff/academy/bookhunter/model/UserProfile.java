package ru.tinkoff.academy.bookhunter.model;

import lombok.*;
import ru.tinkoff.academy.bookhunter.model.enums.Gender;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_profile")
public class UserProfile {
    @Id
    private UUID id;

    @Column(name="nick")
    private String nick;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;

    @Column(name="gender")
    private Gender gender;

    @Column(name="latitude")
    private Double latitude;

    @Column(name="longitude")
    private Double longitude;
}
