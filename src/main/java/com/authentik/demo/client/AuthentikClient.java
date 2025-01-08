package com.authentik.demo.client;

import com.authentik.demo.configuration.properties.AuthentikConfigurationProperties;
import com.authentik.demo.client.dto.AuthentikUserPageResponse;
import com.authentik.demo.client.dto.AuthentikUserResponse;
import com.authentik.demo.client.dto.CreateAuthentikUserRequest;
import com.authentik.demo.client.dto.UpdateAuthentikUserRequest;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthentikClient {

    private static final String EMAIL_PARAM = "email";

    private static final String EMAIL_STAGE_PARAM = "email_stage";

    @Qualifier("authentikRestClient")
    private final RestClient restClient;

    private final AuthentikConfigurationProperties properties;

    public AuthentikUserResponse createUser(String email, boolean enabled) {
        // for simplicity, we will use email as a username and name
        final var request = CreateAuthentikUserRequest.builder()
            .username(email)
            .name(email)
            .email(email)
            .isActive(enabled)
            .groups(List.of(properties.getUserGroup().getId()))
            .type(properties.getUserGroup().getUserType())
            .path(properties.getUserGroup().getName())
            .build();

        return restClient.post()
            .uri(properties.getCreateUserUri())
            .body(request)
            .retrieve()
            .body(AuthentikUserResponse.class);
    }

    public AuthentikUserResponse updateUserGroup(AuthentikUserResponse user, boolean active) {
        final var userGroups = new HashSet<String>();

        if (CollectionUtils.isNotEmpty(user.getGroups())) {
            userGroups.addAll(user.getGroups());
        }

        final var userGroupId = properties.getUserGroup().getId();
        if (!active) {
            userGroups.remove(userGroupId);
        } else {
            userGroups.add(userGroupId);
        }

        final var request = UpdateAuthentikUserRequest.builder()
            .groups(userGroups)
            .build();


        return restClient.patch()
            .uri(properties.getUpdateUserUri(), user.getId())
            .body(request)
            .retrieve()
            .body(AuthentikUserResponse.class);
    }

    public AuthentikUserResponse findUserById(String id) {
        return restClient.get()
            .uri(properties.getFindUserByIdUri(), id)
            .retrieve()
            .body(AuthentikUserResponse.class);
    }

    public AuthentikUserPageResponse findUsersByEmail(String email) {
        final var findUrl = UriComponentsBuilder.fromUriString(properties.getFindUserUri())
            .queryParam(EMAIL_PARAM, email)
            .build()
            .toString();

        final var userPageResponse = restClient.get()
            .uri(findUrl)
            .retrieve()
            .toEntity(AuthentikUserPageResponse.class);

        if (userPageResponse.getBody() == null) {
            log.error("Invalid all users response from authentik {}", userPageResponse);
            throw new IllegalStateException("Invalid all users response from authentik");
        }

        final var existingUsers = userPageResponse.getBody();

        if (existingUsers == null || existingUsers.getResults() == null) {
            log.error("Invalid all users response from authentik {}", existingUsers);
            throw new IllegalStateException("Invalid all users response from authentik");
        }

        if (existingUsers.getResults().size() > 1) {
            log.error("There are more than one user found by email {}", email);
            throw new IllegalStateException("There are more than one user found by email");
        }

        return existingUsers;
    }

    public void sendUserResetPassword(String id) {
        final var uri = UriComponentsBuilder.fromUriString(properties.getResetUserPasswordUri())
            .queryParam(EMAIL_STAGE_PARAM, properties.getEmailStageIdentity())
            .build(id);

        restClient.post()
            .uri(uri)
            .retrieve()
            .toBodilessEntity();
    }
}
