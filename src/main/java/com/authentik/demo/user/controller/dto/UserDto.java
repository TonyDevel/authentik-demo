package com.authentik.demo.user.controller.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class UserDto {
    UUID id;

    String externalId;

    String email;

    Boolean active;
}
