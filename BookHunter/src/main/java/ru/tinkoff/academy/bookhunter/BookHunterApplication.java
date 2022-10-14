package ru.tinkoff.academy.bookhunter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;

@SpringBootApplication
public class BookHunterApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookHunterApplication.class, args);
    }

}
