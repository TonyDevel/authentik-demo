package com.authentik.demo.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateAuthentikUserRequest {
    String username;

    String name;

    String email;

    @JsonProperty("is_active")
    Boolean isActive;

    List<String> groups;

    String path;

    String type;
}
