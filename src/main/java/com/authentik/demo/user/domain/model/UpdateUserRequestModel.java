package com.authentik.demo.user.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class UpdateUserRequestModel {
    UUID id;

    Boolean active;
}
