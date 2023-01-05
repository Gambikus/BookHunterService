package ru.tinkoff.academy.bookhunter.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SystemService {
    private final WebClient webClient;
    private final String baseUrl;

    public SystemService(
            WebClient webClient,
            @Value("${server.port}") String selfPort
    ) {
        this.webClient = webClient;
        this.baseUrl = "http://localhost:" + selfPort + "/actuator/";
    }

    public Mono<String> getSystemResponse(String endpoint) {
        return webClient
                .get()
                .uri(baseUrl + endpoint)
                .retrieve()
                .bodyToMono(String.class);
    }

}
