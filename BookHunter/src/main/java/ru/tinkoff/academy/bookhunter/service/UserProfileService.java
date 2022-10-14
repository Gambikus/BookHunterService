package ru.tinkoff.academy.bookhunter.service;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.converter.UserProfileConverter;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.model.UserProfileDTO;
import ru.tinkoff.academy.bookhunter.repo.UserProfileMapRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileMapRepository userProfileMapRepository;

    @Autowired
    private UserProfileConverter userProfileConverter;

    public Mono<UserProfileDTO> save(UserProfileDTO userProfileDTO) {
        userProfileMapRepository.save(userProfileConverter.convertToEntity(userProfileDTO, null));
        return Mono.just(userProfileDTO);
    }

    public Mono<UserProfileDTO> readById(UUID id) {
        return Mono.just(userProfileConverter.convertToDTO(userProfileMapRepository.findById(id)));
    }

    public Flux<UserProfileDTO> readAll() {
        return Flux.fromIterable(userProfileMapRepository
                .findAll()
                .stream()
                .map(profile -> userProfileConverter.convertToDTO(profile))
                .collect(Collectors.toList()));
    }

    public void deleteById(UUID id) {
        userProfileMapRepository.deleteById(id);
    }

    public Mono<UserProfileDTO> update(UUID id, UserProfileDTO userProfileDTO) {
        userProfileMapRepository.update(id, userProfileConverter.convertToEntity(userProfileDTO, id));
        return Mono.just(userProfileDTO);
    }
}
