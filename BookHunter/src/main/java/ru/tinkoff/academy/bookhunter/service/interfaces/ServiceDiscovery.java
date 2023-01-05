package ru.tinkoff.academy.bookhunter.service.interfaces;

import reactor.core.publisher.Mono;

public interface ServiceDiscovery {
        Mono<String> discoverService();
}
