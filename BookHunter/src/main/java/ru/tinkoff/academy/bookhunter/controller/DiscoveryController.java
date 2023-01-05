package ru.tinkoff.academy.bookhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.tinkoff.academy.bookhunter.service.SystemDiscoveryService;

@RestController
@RequestMapping("/discovery")
@RequiredArgsConstructor
public class DiscoveryController {

    private final SystemDiscoveryService systemDiscoveryService;

    @GetMapping
    public Flux<Object> discoverServices() {
        return systemDiscoveryService.discoverServices();
    }
}
