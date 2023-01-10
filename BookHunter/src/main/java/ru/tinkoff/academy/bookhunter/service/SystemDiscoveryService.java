package ru.tinkoff.academy.bookhunter.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.modelmapper.internal.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SystemDiscoveryService {


    private final List<DefaultServiceDiscovery> servicesForDiscovery;

    public Flux<Map.Entry<String, String>> discoverServices() {

        Flux<String> servicesResponses = Flux.empty();
        for (DefaultServiceDiscovery service:
             servicesForDiscovery) {
            servicesResponses = servicesResponses.mergeWith(service.discoverService());
        }
        Flux<String> servicesNames = Flux.fromIterable(
                servicesForDiscovery
                        .stream()
                        .map(DefaultServiceDiscovery::getServiceName)
                        .collect(Collectors.toList())
        );
        return servicesResponses.zipWith(servicesNames, (response, name) -> new AbstractMap.SimpleEntry<>(name, response));
    }
}
