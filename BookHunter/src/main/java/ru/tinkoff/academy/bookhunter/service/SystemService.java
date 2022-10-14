package ru.tinkoff.academy.bookhunter.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SystemService {
    private final WebClient webClient;
    final String baseUrl = "http://localhost:8080/actuator/";

    public Mono<String> getProbResponse(String endpoint) {
        return webClient
                .get()
                .uri(baseUrl + endpoint)
                .retrieve()
                .bodyToMono(String.class);
    }

    public SystemService() {
        webClient = WebClient.create();
    }
}
