package com.authentik.demo.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CreateUserRequestDto {

    @NotEmpty
    @Email
    String email;

    @NotNull
    Boolean active;
}
