package com.dezaru.GameShop.DTO.user;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.dezaru.GameShop.model.User;

public record UserListDto(Integer id, String name, String email, String createdAt, String role, Boolean isActive) {

    public static List<UserListDto> from(List<User> users) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return users.stream().map(user -> new UserListDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt().format(formatter),
                user.getRole().toString(),
                user.getIsActive())).collect(Collectors.toList());
    }
}