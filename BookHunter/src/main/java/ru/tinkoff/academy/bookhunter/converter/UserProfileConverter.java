package ru.tinkoff.academy.bookhunter.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.bookhunter.model.UserProfile;
import ru.tinkoff.academy.bookhunter.dto.UserProfileDto;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProfileConverter {

    private final ModelMapper modelMapper;

    public UserProfileDto toDto(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        UserProfileDto userProfileDTO = modelMapper.map(userProfile, UserProfileDto.class);
        userProfileDTO.setLocation(userProfile.getLatitude().toString() + ", " + userProfile.getLongitude().toString());
        return userProfileDTO;
    }

    public UserProfile toEntity(UserProfileDto userProfileDto, UUID id) {
        if (userProfileDto == null) {
            return null;
        }
        String[] location = userProfileDto.getLocation().split(", ");
        UserProfile userProfile = modelMapper.map(userProfileDto, UserProfile.class);
        userProfile.setId(Objects.requireNonNullElseGet(id, UUID::randomUUID));
        userProfile.setLatitude(Double.parseDouble(location[0]));
        userProfile.setLongitude(Double.parseDouble(location[1]));
        return userProfile;
    }
}
