package ru.tinkoff.academy.bookhunter.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class EarthDistanceService {

    // Радиус земли в метрах
    private final Double earthRadius = 6371009D;

    /*
        Я считаю расстояние по формуле Хаверсина, а у него погрешность 0,5 процента,
        так что такая погрешность для сравнения
     */
    private final double errorForDistanceComparing = 0.005;

    public Double getDistanceBetweenTwoObjects(Double latitude1,
                                               Double longitude1,
                                               Double latitude2,
                                               Double longitude2) {

        latitude1 = latitude1 * 2 * Math.PI / 360;
        latitude2 = latitude2 * 2 * Math.PI / 360;
        longitude2 = longitude2 * 2 * Math.PI / 360;
        longitude1 = longitude1 * 2 * Math.PI / 360;

        double deltaLatitude = latitude1 - latitude2;
        double meanLongitude = (longitude1 - longitude2) / 2;
        double meanLatitude = (latitude1 - latitude2) / 2;

        return earthRadius * 2
                * Math.asin(Math.sqrt(Math.pow(Math.sin(meanLatitude), 2)
                        + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(meanLongitude), 2)));
    }

    public int compareWithError(Double firstNumber, Double secondNumber) {
        if (firstNumber * (1 + errorForDistanceComparing) < secondNumber) {
            return -1;
        }
        if (firstNumber * (1 - errorForDistanceComparing) > secondNumber) {
            return 1;
        }
        return 0;
    }
}
