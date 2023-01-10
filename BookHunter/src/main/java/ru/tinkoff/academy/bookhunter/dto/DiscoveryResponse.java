package ru.tinkoff.academy.bookhunter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
public class DiscoveryResponse {

    private Mono<String> bookShelfService;

    private Mono<String> blackBooksService;

}
