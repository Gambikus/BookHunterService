package ru.tinkoff.academy.bookhunter.repo;


import org.springframework.stereotype.Component;
import ru.tinkoff.academy.bookhunter.exception.UserProfileNotFoundException;
import ru.tinkoff.academy.bookhunter.model.UserProfile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class UserProfileMap {
    private final Map<UUID, UserProfile> repo = new HashMap<>();

    public UserProfile findById(UUID id) {
        if (!repo.containsKey(id)) {
            throw new UserProfileNotFoundException("id" + id.toString());
        }
        return repo.get(id);
    }

    public List<UserProfile> findAll() {
        return repo.values().stream().toList();
    }

    public UUID save(UserProfile userProfile) {
        repo.put(userProfile.getId(), userProfile);
        return userProfile.getId();
    }

    public void deleteById(UUID id) {
        if (!repo.containsKey(id)) {
            throw new UserProfileNotFoundException("id" + id.toString());
        }
        repo.remove(id);
    }

    public void update(UUID id, UserProfile userProfile) {
        if (!repo.containsKey(id)) {
            throw new UserProfileNotFoundException("id" + id.toString());
        }
        repo.put(id, userProfile);
    }

    public UserProfile findByLocation(Double latitude, Double longitude) {
        for (UserProfile currentUserProfile:
             repo.values()) {
            if (currentUserProfile.getLatitude().equals(latitude)
                    && currentUserProfile.getLongitude().equals(longitude)) {
                return currentUserProfile;
            }
        }
        throw new UserProfileNotFoundException("location" + latitude.toString() + ", " + longitude.toString());
    }

    public void deleteAll() {
        repo.clear();
    }
}
