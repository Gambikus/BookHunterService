package ru.tinkoff.academy.bookhunter.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.model.UserProfileDTO;
import ru.tinkoff.academy.bookhunter.service.UserProfileService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping
    public Flux<UserProfileDTO> getAllUserProfile() {
        return userProfileService.readAll();
    }

    @GetMapping("/{id}")
    public Mono<UserProfileDTO> getUserProfile(@PathVariable UUID id) {
        return userProfileService.readById(id);
    }

    @PostMapping
    public Mono<UserProfileDTO> createUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
        return userProfileService.save(userProfileDTO);
    }

    @PutMapping("/{id}")
    public Mono<UserProfileDTO> updateUserProfile(@PathVariable UUID id, @RequestBody UserProfileDTO userProfileDTO) {
        return userProfileService.update(id, userProfileDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUserProfile(@PathVariable UUID id) {
        userProfileService.deleteById(id);
    }
}
