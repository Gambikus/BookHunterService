package ru.tinkoff.academy.bookhunter.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EarthDistanceServiceTest {

    private static EarthDistanceService subject;

    private double distance;

    private double latitude1, latitude2;

    private double longitude1, longitude2;

    @BeforeAll
    public static void init() {
        subject = new EarthDistanceService();
    }

    @Test
    public void testGettingDistanceWithPositiveParameters() {
        givenPositiveParameters();

        whenCalculateDistance();

        thenDistanceShouldBe(634600D);
    }

    private void givenPositiveParameters() {
        latitude1 = 55.7522;
        longitude1 = 37.6156;
        latitude2 = 59.89444;
        longitude2 = 30.26417;
    }

    private void whenCalculateDistance() {
        distance = subject.getDistanceBetweenTwoObjects(
                latitude1,
                longitude1,
                latitude2,
                longitude2
        );
    }
    private void thenDistanceShouldBe(double rightDistance) {
        Assertions.assertEquals(0, subject.compareWithError(distance, rightDistance));
    }

    @Test
    public void testGettingDistanceWithNegativeParameters() {
        givenNegativeParameters();

        whenCalculateDistance();

        thenDistanceShouldBe(2235000D);
    }

    private void givenNegativeParameters() {
        latitude1 = -55.7522;
        longitude1 = -67.6156;
        latitude2 = -59.89444;
        longitude2 = -30.26417;
    }

    @Test
    public void testGettingDistanceWithSameParameters() {
        givenSameParameters();

        whenCalculateDistance();

        thenDistanceShouldBe(0);
    }

    private void givenSameParameters() {
        latitude1 = 0;
        longitude1 = 0;
        latitude2 = 0;
        longitude2 = 0;
    }
}
