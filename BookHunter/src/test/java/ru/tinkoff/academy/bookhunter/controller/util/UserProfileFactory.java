package ru.tinkoff.academy.bookhunter.controller.util;

import ru.tinkoff.academy.bookhunter.dto.UserProfileDto;
import ru.tinkoff.academy.bookhunter.model.enums.Gender;

import java.util.Random;

public class UserProfileFactory {

    private final static String[] possibleNames = {
            "Vasya",
            "Petya",
            "Sasha",
            "Dima",
            "George",
            "Anton",
            "John",
            "Bob"
    };

    private final static String[] possibleNicks = {
            "CoolNick",
            "BadNick",
            "GreatNick",
            "YetAnotherNick",
            "Noname",
            "UnknownNick",
            "AwesomeNick"
    };

    public static UserProfileDto CreateRandomDto() {
        Random rand = new Random();
        return new UserProfileDto(
                possibleNicks[rand.nextInt(possibleNicks.length)],
                possibleNames[rand.nextInt(possibleNames.length)],
                1 + rand.nextInt(100),
                Gender.values()[rand.nextInt(3)],
                (rand.nextDouble() * 360D - 180D) + ", " +
                        (rand.nextDouble() * 180D - 90D)
        );
    }
}
