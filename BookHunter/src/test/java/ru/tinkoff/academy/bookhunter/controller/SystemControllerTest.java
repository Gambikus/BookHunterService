package ru.tinkoff.academy.bookhunter.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class SystemControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private WebTestClient.ResponseSpec responseSpec;

    @Test
    public void testLivenessStatus() {
        whenGetRequest("/system/liveness");

        thenShouldBeUp();
    }

    private void whenGetRequest(String stringPath) {
        responseSpec = webTestClient.get()
                .uri(stringPath)
                .exchange();
    }

    private void thenShouldBeUp() {
        responseSpec
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("{\"status\":\"UP\"}");
    }

    @Test
    public void testReadinessStatus() {
        /*
            Почему-то не работает WebTestClient в связке с WebClient'ом
         */
        whenGetRequest("/system/readiness");

        thenShouldBeUp();
    }

}
