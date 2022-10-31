package ru.tinkoff.academy.bookhunter.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.DTO.UserProfileDTO;
import ru.tinkoff.academy.bookhunter.service.UserProfileService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping
    public Flux<UserProfileDTO> getAllUserProfile() {
        return userProfileService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<UserProfileDTO> getUserProfile(@PathVariable UUID id) {
        return userProfileService.findById(id);
    }

    @PostMapping
    public Mono<UUID> createUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
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
