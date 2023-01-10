package ru.tinkoff.academy.bookhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.tinkoff.academy.bookhunter.converter.UserProfileConverter;
import ru.tinkoff.academy.bookhunter.exception.UserProfileNotFoundException;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.dto.UserProfileDto;
import ru.tinkoff.academy.bookhunter.repo.UserProfileRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserNearestService {


    private final UserProfileRepository userProfileRepository;

    private final UserProfileConverter userProfileConverter;

    private final EarthDistanceService earthDistanceService;

    public Flux<UserProfileDto> getNearestToId(UUID id, Long distance, Long amount) {
        List<UserProfile> userProfiles = new ArrayList<>(userProfileRepository.findAll());
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        if (userProfile.isEmpty()) {
            throw new UserProfileNotFoundException("id " + id.toString());
        }
        UserProfile mainUserProfile = userProfile.get();
        Comparator<UserProfile> byDistance = generateUserProfileComparator(mainUserProfile);

        userProfiles.sort(byDistance);
        List<UserProfileDto> result = new ArrayList<>();

        for (int i = 0; i < userProfiles.size() && result.size() < amount; i++) {
            UserProfile currentUserProfile = userProfiles.get(i);
            if (currentUserProfile.getId().equals(id)) {
                continue;
            }
            double currentDistance = earthDistanceService.getDistanceBetweenTwoObjects(
                    currentUserProfile.getLatitude(),
                    currentUserProfile.getLongitude(),
                    mainUserProfile.getLatitude(),
                    mainUserProfile.getLongitude());
            if (earthDistanceService.compareWithError(currentDistance, (double)distance) == 1) {
                break;
            }
            result.add(userProfileConverter.toDto(currentUserProfile));
        }

        return Flux.fromIterable(result);
    }

    private Comparator<UserProfile> generateUserProfileComparator(UserProfile userProfile) {
        return (userProfile1, userProfile2) ->
                Double.compare(
                        earthDistanceService
                                .getDistanceBetweenTwoObjects(
                                        userProfile1.getLatitude(),
                                        userProfile1.getLongitude(),
                                        userProfile.getLatitude(),
                                        userProfile.getLongitude()),
                        earthDistanceService
                                .getDistanceBetweenTwoObjects(
                                        userProfile2.getLatitude(),
                                        userProfile2.getLongitude(),
                                        userProfile.getLatitude(),
                                        userProfile.getLongitude())
                );
    }

    public Flux<UserProfileDto> getNearestToLocation(Double latitude, Double longitude, Long distance, Long amount) {
        Optional<UserProfile> mainUserProfile = userProfileRepository.findByLongitudeAndLatitude(longitude, latitude);
        if (mainUserProfile.isEmpty()) {
            throw new UserProfileNotFoundException("location " + latitude.toString() + ", " + longitude.toString());
        }
        return this.getNearestToId(mainUserProfile.get().getId(), distance, amount);
    }
}
