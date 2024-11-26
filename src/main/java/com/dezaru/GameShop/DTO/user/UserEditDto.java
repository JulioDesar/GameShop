package com.dezaru.GameShop.DTO.user;

import jakarta.validation.constraints.NotBlank;

public record UserEditDto(

        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        String Role,
        Boolean isActive) {

}
