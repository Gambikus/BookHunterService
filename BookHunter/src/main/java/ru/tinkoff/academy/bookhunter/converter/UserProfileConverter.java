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
        return modelMapper.map(userProfile, UserProfileDto.class);
    }

    public UserProfile toEntity(UserProfileDto userProfileDto, UUID id) {
        if (userProfileDto == null) {
            return null;
        }
        UserProfile userProfile = modelMapper.map(userProfileDto, UserProfile.class);
        userProfile.setId(Objects.requireNonNullElseGet(id, UUID::randomUUID));
        return userProfile;
    }
}
