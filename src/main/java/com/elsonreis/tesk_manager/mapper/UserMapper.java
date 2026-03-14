package com.elsonreis.tesk_manager.mapper;

import com.elsonreis.tesk_manager.dto.request.UserRequest;
import com.elsonreis.tesk_manager.dto.response.UserResponse;
import com.elsonreis.tesk_manager.entity.User;

public class UserMapper {

    public static UserResponse toDTO(User user) {

        UserResponse dto = new UserResponse();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        // Não incluímos a senha no DTO por questões de segurança
        return dto;
    }

    public static User toEntity(UserRequest dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }
}
