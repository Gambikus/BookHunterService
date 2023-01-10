package ru.tinkoff.academy.bookhunter.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.service.SystemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system")
@Tag(name="System")
public class SystemController {

    private final SystemService systemService;

    @GetMapping(value = "/liveness", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<String> getLivenessStatus() {
        return systemService.getSystemResponse("health/liveness");
    }

    @GetMapping(value = "/readiness", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<String> getReadinessStatus() {
        return systemService.getSystemResponse("health/readiness");
    }


    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<String> getHealthStatus() {
        return systemService.getSystemResponse("info");
    }
}
