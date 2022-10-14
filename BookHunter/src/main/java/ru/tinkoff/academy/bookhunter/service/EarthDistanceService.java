package ru.tinkoff.academy.bookhunter.service;

import org.springframework.stereotype.Service;

@Service
public class EarthDistanceService {
    private final Double earthRadius = 6371009D;

    public Double getDistanceBetweenTwoObjects(Double latitude1,
                                               Double longitude1,
                                               Double latitude2,
                                               Double longitude2) {
        double deltaLatitude = latitude1 - latitude2;
        double deltaLongitude = longitude1 - longitude2;
        double meanLatitude = (latitude1 + latitude1) / 2;
        return earthRadius *
                Math.sqrt(Math.pow(deltaLatitude, 2) + Math.pow(Math.cos(meanLatitude) * deltaLongitude, 2));
    }
}
