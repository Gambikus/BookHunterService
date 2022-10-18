package ru.tinkoff.academy.bookhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.tinkoff.academy.bookhunter.model.UserProfileDTO;
import ru.tinkoff.academy.bookhunter.service.UserNearestService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserNearestController {

    private final UserNearestService userNearestService;

    @GetMapping("/{id}/nearest")
    public Flux<UserProfileDTO> getNearest(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "100") Long distance,
            @RequestParam(required = false, defaultValue = "50") Long amount
            ){
        return userNearestService.getNearestToId(id, distance, amount);
    }

    @GetMapping("/nearest")
    public Flux<UserProfileDTO> getNearest(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(required = false, defaultValue = "100") Long distance,
            @RequestParam(required = false, defaultValue = "50") Long amount
            ){
        return userNearestService.getNearestToLocation(latitude, longitude, distance, amount);
    }
}
