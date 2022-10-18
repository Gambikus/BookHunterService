package ru.tinkoff.academy.bookhunter.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/liveness")
    Mono<String> getLivenessStatus() {
        return systemService.getProbResponse("health/liveness");
    }

    @GetMapping("/readiness")
    Mono<String> getReadinessStatus() {
        return systemService.getProbResponse("health/readiness");
    }


    @GetMapping("/version")
    Mono<String> getHealthStatus() {
        return systemService.getProbResponse("info");
    }
}
