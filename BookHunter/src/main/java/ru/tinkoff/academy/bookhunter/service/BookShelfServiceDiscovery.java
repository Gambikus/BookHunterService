package ru.tinkoff.academy.bookhunter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.bookhunter.service.interfaces.ServiceDiscovery;

@Service
public class BookShelfServiceDiscovery extends DefaultServiceDiscovery {

    public BookShelfServiceDiscovery (
            WebClient webClient,
            @Value("${service.bookShelf.url}") String url
    ) {
        super(webClient, url, "BookShelfService");
    }

}
