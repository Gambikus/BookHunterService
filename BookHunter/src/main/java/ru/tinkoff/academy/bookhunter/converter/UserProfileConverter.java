package ru.tinkoff.academy.bookhunter.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.DTO.UserProfileDTO;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProfileConverter {

    private final ModelMapper modelMapper;

    public UserProfileDTO toDTO(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        UserProfileDTO userProfileDTO = modelMapper.map(userProfile, UserProfileDTO.class);
        userProfileDTO.setLocation(userProfile.getLatitude().toString() + ", " + userProfile.getLongitude().toString());
        return userProfileDTO;
    }

    public UserProfile toEntity(UserProfileDTO userProfileDTO, UUID id) {
        if (userProfileDTO == null) {
            return null;
        }
        String[] location = userProfileDTO.getLocation().split(", ");
        UserProfile userProfile = modelMapper.map(userProfileDTO, UserProfile.class);
        userProfile.setId(Objects.requireNonNullElseGet(id, UUID::randomUUID));
        userProfile.setLatitude(Double.parseDouble(location[0]));
        userProfile.setLongitude(Double.parseDouble(location[1]));
        return userProfile;
    }
}
