package ru.tinkoff.academy.bookhunter.service.interfaces;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


public interface ServiceDiscovery {
        Mono<String> discoverService();
}
