package ru.tinkoff.academy.bookhunter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.service.interfaces.ServiceDiscovery;

@Service
public class BlackBooksServiceDiscovery implements ServiceDiscovery {
    private final WebClient webClient;
    private final String url;

    public BlackBooksServiceDiscovery (
            WebClient webClient,
            @Value("${service.blackBooks.url}") String url
    ) {
        this.webClient = webClient;
        this.url = url;
    }

    public Mono<String> discoverService() {
        Mono<String> livenessResponse = getLivenessResponse();

        Mono<String> versionResponse = getVersionResponse();
        return livenessResponse
                .filter(msg -> !msg.contains("UP"))
                .switchIfEmpty(versionResponse)
                .onErrorReturn("BookShelfService is not available.");
    }

    private Mono<String> getVersionResponse() {
        return webClient
                .get()
                .uri(url + "/system/info")
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<String> getLivenessResponse() {

        return webClient
                .get()
                .uri(url + "/system/liveness")
                .retrieve()
                .bodyToMono(String.class);
    }
}
