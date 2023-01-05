package ru.tinkoff.academy.bookhunter.controller;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.dto.UserProfileDto;
import ru.tinkoff.academy.bookhunter.model.enums.Gender;
import ru.tinkoff.academy.bookhunter.repo.UserProfileMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureWebTestClient
public class UserNearestControllerTest {

    @Autowired
    private UserProfileMap userProfileMap;

    @Autowired
    private WebTestClient webTestClient;

    private List<UserProfileDto> DTOs;

    private WebTestClient.ResponseSpec responseSpec;

    private UUID id;

    @AfterEach
    public void Clear() {
        userProfileMap.deleteAll();
    }

    private void givenCreationUserProfiles() {
        DTOs = new ArrayList<>(Arrays.asList(
                new UserProfileDto(
                        "gambikus",
                        "Vasya",
                        18,
                        Gender.MALE,
                        "41.0, 63.9"
                ),
                new UserProfileDto(
                        "example",
                        "Petya",
                        22,
                        Gender.HIDDEN,
                        "71.0, 67.0"
                ),
                new UserProfileDto(
                        "login",
                        "Sasha",
                        5,
                        Gender.FEMALE,
                        "41.0, 64.0"
                )
        ));

        for (UserProfileDto DTO:
                DTOs) {
            responseSpec = webTestClient.post()
                    .uri("/user/profile")
                    .body(Mono.just(DTO), UserProfileDto.class)
                    .exchange();
        }
        id = responseSpec.returnResult(UUID.class).getResponseBody().blockFirst();
    }

    @Test
    public void testGettingNearestById() {
        givenCreationUserProfiles();

        whenGetNearestById(10000L);

        thenResponseShouldContainOneUserProfile();
    }

    private void whenGetNearestById(Long distance) {
        responseSpec = webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/user/{id}/nearest")
                                .queryParam("distance", distance)
                                .build(id))
                .exchange();
    }

    private void thenResponseShouldContainOneUserProfile() {
        responseSpec
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Vasya");
    }

    @Test
    public void getZeroNearestById() {
        givenCreationUserProfiles();

        whenGetNearestById(100L);

        thenResponseShouldContainNoUserProfiles();
    }

    private void thenResponseShouldContainNoUserProfiles() {
        responseSpec
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    public void testGettingNearestByLocation() {
        givenCreationUserProfiles();

        whenGetNearestByLocation(41.0,64.0,10000L);

        thenResponseShouldContainOneUserProfile();
    }

    private void whenGetNearestByLocation(Double latitude, Double longitude, Long distance) {
        responseSpec = webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/user/nearest")
                                .queryParam("latitude", latitude)
                                .queryParam("longitude", longitude)
                                .queryParam("distance", distance)
                                .build(id))
                .exchange();
    }
}
