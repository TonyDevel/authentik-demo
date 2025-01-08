package com.authentik.demo.user.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserRequestModel {
    String email;

    Boolean active;
}
