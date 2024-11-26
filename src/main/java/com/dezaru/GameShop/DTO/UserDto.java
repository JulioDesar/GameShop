package com.dezaru.GameShop.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserDto(

        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        @Min(6)
        String password,
        String role
) {
}
