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
public class UserProfileControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private UserProfileMap userProfileMap;

    private UserProfileDto userProfileDTO;

    private WebTestClient.ResponseSpec responseSpec;

    private UUID userProfileId;

    private List<UserProfileDto> DTOs;

    @AfterEach
    public void clear() {
        userProfileMap.deleteAll();
        userProfileDTO = null;
        userProfileId = null;
        responseSpec = null;
        DTOs = null;
    }

    @Test
    public void testCreateUserProfile() {
        givenUserProfileDTO();

        whenCreateUserProfile();

        thenCreationShouldBeSuccessful();
    }

    private void givenUserProfileDTO() {
        userProfileDTO = new UserProfileDto(
                "gambikus",
                "Vasya",
                18,
                Gender.MALE,
                "60.0, 60.0"
        );
    }

    private void whenCreateUserProfile() {
        responseSpec = webTestClient.post()
                .uri("/user/profile")
                .body(Mono.just(userProfileDTO), UserProfileDto.class)
                .exchange();
    }
    private void thenCreationShouldBeSuccessful() {
        responseSpec
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UUID.class);
    }

    @Test
    public void testGetUserProfile() {
        givenCreationUserProfileAndId();

        whenGetUserProfileById();

        thenUserProfilesShouldBeEquals();
    }

    private void givenCreationUserProfileAndId() {
        givenUserProfileDTO();
        whenCreateUserProfile();
        givenUserProfileId();
    }

    private void givenUserProfileId() {
        userProfileId = responseSpec.returnResult(UUID.class).getResponseBody().blockFirst();
    }

    private void whenGetUserProfileById() {
        responseSpec = webTestClient.get()
                .uri("/user/profile/{id}", userProfileId)
                .exchange();
    }

    private void thenUserProfilesShouldBeEquals() {
        responseSpec
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserProfileDto.class)
                .isEqualTo(userProfileDTO);
    }

    @Test
    public void testDeleteUserProfile() {
        givenCreationUserProfileAndId();

        whenDeleteUserProfileById();

        thenGetRequestShouldBeError();
    }

    private void whenDeleteUserProfileById() {
        webTestClient
                .delete()
                .uri("/user/profile/{id}", userProfileId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    private void thenGetRequestShouldBeError() {
        webTestClient
                .get()
                .uri("/user/profile/{id}", userProfileId)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    public void testUpdateUserProfile() {
        givenCreationUserProfileAndUpdateUserProfile();

        whenUpdateUserProfileById();

        thenGetRequestShouldReturnUpdatedUserProfile();
    }

    private void givenCreationUserProfileAndUpdateUserProfile() {
        givenCreationUserProfileAndId();
        userProfileDTO = new UserProfileDto(
                "gambikus2.0",
                "Vasya",
                18,
                Gender.MALE,
                "61.0, 60.0"
        );

    }

    private void whenUpdateUserProfileById() {
        responseSpec = webTestClient
                .put()
                .uri("/user/profile/{id}", userProfileId)
                .body(Mono.just(userProfileDTO), UserProfileDto.class)
                .exchange();
    }

    private void thenGetRequestShouldReturnUpdatedUserProfile() {
        webTestClient
                .get()
                .uri("/user/profile/{id}", userProfileId)
                .exchange()
                .expectBody(UserProfileDto.class)
                .isEqualTo(userProfileDTO);
    }

    @Test
    public void testGettingThreeUserProfiles() {
        givenCreationOfThreeUserProfiles();

        whenGetAllUserProfiles();

        thenGetRequestShouldContainThreeUserProfiles();
    }

    private void givenCreationOfThreeUserProfiles() {
        DTOs = new ArrayList<>(Arrays.asList(
                new UserProfileDto(
                        "gambikus",
                        "Vasya",
                        18,
                        Gender.MALE,
                        "61.0, 60.0"
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
            userProfileDTO = DTO;
            whenCreateUserProfile();
        }
    }

    private void whenGetAllUserProfiles() {
        responseSpec = webTestClient
                .get()
                .uri("/user/profile")
                .exchange();
    }

    private void thenGetRequestShouldContainThreeUserProfiles() {
        responseSpec
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.length()")
                .isEqualTo(3);

    }
}
