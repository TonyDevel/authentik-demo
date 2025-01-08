package com.authentik.demo.user.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class UpdateUserRequestDto {

    @NotNull
    UUID id;

    @NotNull
    Boolean active;
}
