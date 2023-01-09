package ru.tinkoff.academy.bookhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.service.interfaces.ServiceDiscovery;

@RequiredArgsConstructor
public abstract class DefaultServiceDiscovery implements ServiceDiscovery {
    private final WebClient webClient;
    private final String url;
    private final String serviceName;
    private final String upStatus = "UP";

    public Mono<String> discoverService() {
        Mono<String> livenessResponse = getLivenessResponse();

        Mono<String> versionResponse = getVersionResponse();
        return livenessResponse
                .filter(msg -> !msg.contains(upStatus))
                .switchIfEmpty(versionResponse)
                .onErrorReturn(serviceName + " is not available.");
    }

    public String getServiceName() {
        return serviceName;
    }

    private Mono<String> getVersionResponse() {
        return webClient
                .get()
                .uri(url + "/system/version")
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
