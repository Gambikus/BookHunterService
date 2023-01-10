package ru.tinkoff.academy.bookhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.converter.UserProfileConverter;
import ru.tinkoff.academy.bookhunter.dto.UserProfileDto;
import ru.tinkoff.academy.bookhunter.exception.UserProfileNotFoundException;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.repo.UserProfileMap;
import ru.tinkoff.academy.bookhunter.repo.UserProfileRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final UserProfileConverter userProfileConverter;

    public Mono<UUID> save(UserProfileDto userProfileDTO) {
        return Mono.just(userProfileRepository.save(userProfileConverter.toEntity(userProfileDTO, null)).getId());
    }

    public Mono<UserProfileDto> findById(UUID id) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        if (userProfile.isEmpty()) {
            throw new UserProfileNotFoundException("id " + id.toString());
        }
        return Mono.just(userProfileConverter.toDto(userProfile.get()));
    }

    public Flux<Map.Entry<UUID, UserProfileDto>> findAll() {
        return Flux.fromIterable(userProfileRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(UserProfile::getId, userProfileConverter::toDto))
                .entrySet());
    }

    public void deleteById(UUID id) {
        userProfileRepository.deleteById(id);
    }

    public Mono<UserProfileDto> update(UUID id, UserProfileDto userProfileDTO) {
        deleteById(id);
        userProfileRepository.save(userProfileConverter.toEntity(userProfileDTO, id));
        return Mono.just(userProfileDTO);
    }
}
