package com.authentik.demo.configuration.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties("app.authentik")
public class AuthentikConfigurationProperties {

    @NotEmpty
    private String baseUrl;

    @NotEmpty
    private String findUserUri;

    @NotEmpty
    private String findUserByIdUri;

    @NotEmpty
    private String createUserUri;

    @NotEmpty
    private String updateUserUri;

    @NotEmpty
    private String resetUserPasswordUri;

    @NotEmpty
    private String apiToken;

    @NotEmpty
    private String emailStageIdentity;

    @NotNull
    private UserGroup userGroup;

    @Data
    @Validated
    public static class UserGroup {

        @NotEmpty
        private String authorizationEntryPoint;

        @NotEmpty
        private String id;

        @NotEmpty
        private String name;

        @NotEmpty
        private String userType;
    }
}
