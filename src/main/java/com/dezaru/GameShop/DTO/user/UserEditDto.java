package com.dezaru.GameShop.DTO.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserEditDto(

        @NotBlank String name,
        @NotBlank String email,
        @NotNull String password,
        String role,
        Boolean isActive) {

}
