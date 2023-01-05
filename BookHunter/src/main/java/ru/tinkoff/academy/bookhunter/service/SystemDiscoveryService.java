package ru.tinkoff.academy.bookhunter.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.modelmapper.internal.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class SystemDiscoveryService {

    private final BlackBooksServiceDiscovery blackBooksServiceDiscovery;

    private final BookShelfServiceDiscovery bookShelfServiceDiscovery;

    public Flux<Object> discoverServices() {
        Flux<String> a = bookShelfServiceDiscovery.discoverService().mergeWith(blackBooksServiceDiscovery.discoverService());
        Flux<String> servicesNames = Flux.fromIterable(
                new ArrayList<>(
                        Arrays.asList(
                                "BookShelfService",
                                "BlackBooksService"
                        )
                )
        );
        return a.zipWith(servicesNames, (response, name) -> new AbstractMap.SimpleEntry<>(name, response));

    }
}
