package ru.tinkoff.academy.bookhunter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.tinkoff.academy.bookhunter.converter.UserProfileConverter;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.model.UserProfileDTO;
import ru.tinkoff.academy.bookhunter.repo.UserProfileMapRepository;

import java.util.*;

@Service
public class UserNearestService {

    @Autowired
    UserProfileMapRepository userProfileMapRepository;

    @Autowired
    UserProfileConverter userProfileConverter;

    @Autowired
    EarthDistanceService earthDistanceService;

    public Flux<UserProfileDTO> getNearestToId(UUID id, Long distance, Long amount) {
        List<UserProfile> userProfiles = new ArrayList<>(userProfileMapRepository.findAll());
        UserProfile mainUserProfile = userProfileMapRepository.findById(id);
        Comparator<UserProfile> byDistance =
                (userProfile1,
                 userProfile2) ->
                        Double.compare(earthDistanceService
                                        .getDistanceBetweenTwoObjects(
                                                userProfile1.getLatitude(),
                                                userProfile1.getLongitude(),
                                                mainUserProfile.getLatitude(),
                                                mainUserProfile.getLongitude()),
                                earthDistanceService.
                                        getDistanceBetweenTwoObjects(
                                                userProfile2.getLatitude(),
                                                userProfile2.getLongitude(),
                                                mainUserProfile.getLatitude(),
                                                mainUserProfile.getLongitude()));
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
            result.add(userProfileConverter.convertToDTO(currentUserProfile));
        }
        return Flux.fromIterable(result);
    }

    public Flux<UserProfileDTO> getNearestToLocation(Double latitude, Double longitude, Long distance, Long amount) {
        UserProfile mainUserProfile = userProfileMapRepository.findByLocation(latitude, longitude);
        return this.getNearestToId(mainUserProfile.getId(), distance, amount);
    }
}
