package ru.tinkoff.academy.bookhunter.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.dto.UserProfileDto;
import ru.tinkoff.academy.bookhunter.service.UserProfileService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping
    public Flux<Map.Entry<UUID, UserProfileDto>> getAllUserProfile() {
        return userProfileService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<UserProfileDto> getUserProfile(@PathVariable UUID id) {
        return userProfileService.findById(id);
    }

    @PostMapping
    public Mono<UUID> createUserProfile(@RequestBody UserProfileDto userProfileDTO) {
        return userProfileService.save(userProfileDTO);
    }

    @PutMapping("/{id}")
    public Mono<UserProfileDto> updateUserProfile(@PathVariable UUID id, @RequestBody UserProfileDto userProfileDTO) {
        return userProfileService.update(id, userProfileDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUserProfile(@PathVariable UUID id) {
        userProfileService.deleteById(id);
    }
}
