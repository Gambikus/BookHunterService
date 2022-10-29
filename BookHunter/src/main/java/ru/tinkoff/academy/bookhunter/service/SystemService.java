package ru.tinkoff.academy.bookhunter.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final WebClient webClient;
    final String baseUrl = "http://localhost:8080/actuator/";

    public Mono<String> getSystemResponse(String endpoint) {
        return webClient
                .get()
                .uri(baseUrl + endpoint)
                .retrieve()
                .bodyToMono(String.class);
    }

}
