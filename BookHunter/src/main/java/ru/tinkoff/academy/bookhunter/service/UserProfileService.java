package ru.tinkoff.academy.bookhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.converter.UserProfileConverter;
import ru.tinkoff.academy.bookhunter.DTO.UserProfileDTO;
import ru.tinkoff.academy.bookhunter.repo.UserProfileMap;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileMap userProfileMap;

    private final UserProfileConverter userProfileConverter;

    public Mono<UUID> save(UserProfileDTO userProfileDTO) {
        return Mono.just(userProfileMap.save(userProfileConverter.toEntity(userProfileDTO, null)));
    }

    public Mono<UserProfileDTO> findById(UUID id) {
        return Mono.just(userProfileConverter.toDTO(userProfileMap.findById(id)));
    }

    public Flux<UserProfileDTO> findAll() {
        return Flux.fromIterable(userProfileMap
                .findAll()
                .stream()
                .map(profile -> userProfileConverter.toDTO(profile))
                .collect(Collectors.toList()));
    }

    public void deleteById(UUID id) {
        userProfileMap.deleteById(id);
    }

    public Mono<UserProfileDTO> update(UUID id, UserProfileDTO userProfileDTO) {
        userProfileMap.update(id, userProfileConverter.toEntity(userProfileDTO, id));
        return Mono.just(userProfileDTO);
    }
}
