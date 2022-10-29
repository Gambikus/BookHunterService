package ru.tinkoff.academy.bookhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.tinkoff.academy.bookhunter.converter.UserProfileConverter;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.DTO.UserProfileDTO;
import ru.tinkoff.academy.bookhunter.repo.UserProfileMap;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserNearestService {

    private final UserProfileMap userProfileMap;

    private final UserProfileConverter userProfileConverter;

    private final EarthDistanceService earthDistanceService;

    public Flux<UserProfileDTO> getNearestToId(UUID id, Long distance, Long amount) {
        List<UserProfile> userProfiles = new ArrayList<>(userProfileMap.findAll());
        UserProfile mainUserProfile = userProfileMap.findById(id);

        Comparator<UserProfile> byDistance = generateUserProfileComparator(mainUserProfile);

        userProfiles.sort(byDistance);
        List<UserProfileDTO> result = new ArrayList<>();

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
            if ((long)Math.ceil(currentDistance) > distance) {
                break;
            }
            result.add(userProfileConverter.toDTO(currentUserProfile));
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

    public Flux<UserProfileDTO> getNearestToLocation(Double latitude, Double longitude, Long distance, Long amount) {
        UserProfile mainUserProfile = userProfileMap.findByLocation(latitude, longitude);
        return this.getNearestToId(mainUserProfile.getId(), distance, amount);
    }
}
