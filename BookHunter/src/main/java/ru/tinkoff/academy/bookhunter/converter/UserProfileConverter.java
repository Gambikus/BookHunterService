package ru.tinkoff.academy.bookhunter.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.model.UserProfileDTO;

import java.util.Objects;
import java.util.UUID;

@Component
public class UserProfileConverter {

    private final ModelMapper modelMapper = new ModelMapper();

    public UserProfileDTO convertToDTO(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        UserProfileDTO userProfileDTO = modelMapper.map(userProfile, UserProfileDTO.class);
        userProfileDTO.setLocation(userProfile.getLatitude().toString() + ", " + userProfile.getLongitude().toString());
        return userProfileDTO;
    }

    public UserProfile convertToEntity(UserProfileDTO userProfileDTO, UUID id) {
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
