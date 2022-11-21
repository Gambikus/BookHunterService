package ru.tinkoff.academy.bookhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.converter.UserProfileConverter;
import ru.tinkoff.academy.bookhunter.dto.UserProfileDto;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.repo.UserProfileMap;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileMap userProfileMap;

    private final UserProfileConverter userProfileConverter;

    public Mono<UUID> save(UserProfileDto userProfileDTO) {
        return Mono.just(userProfileMap.save(userProfileConverter.toEntity(userProfileDTO, null)));
    }

    public Mono<UserProfileDto> findById(UUID id) {
        return Mono.just(userProfileConverter.toDto(userProfileMap.findById(id)));
    }

    public Flux<Map.Entry<UUID, UserProfileDto>> findAll() {
        return Flux.fromIterable(userProfileMap
                .findAll()
                .stream()
                .collect(Collectors.toMap(UserProfile::getId, userProfileConverter::toDto))
                .entrySet());
    }

    public void deleteById(UUID id) {
        userProfileMap.deleteById(id);
    }

    public Mono<UserProfileDto> update(UUID id, UserProfileDto userProfileDTO) {
        userProfileMap.update(id, userProfileConverter.toEntity(userProfileDTO, id));
        return Mono.just(userProfileDTO);
    }
}
