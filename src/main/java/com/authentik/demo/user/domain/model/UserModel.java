package com.authentik.demo.user.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class UserModel {
    UUID id;

    String externalId;

    String email;

    Boolean active;
}
