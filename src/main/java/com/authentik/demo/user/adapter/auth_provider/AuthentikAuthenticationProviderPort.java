package com.authentik.demo.user.adapter.auth_provider;

import com.authentik.demo.client.AuthentikClient;
import com.authentik.demo.user.adapter.auth_provider.mapper.AuthentikAuthenticationProviderPortMapper;
import com.authentik.demo.user.domain.model.CreateUserRequestModel;
import com.authentik.demo.user.domain.model.ExternalProviderUserModel;
import com.authentik.demo.user.domain.port.AuthenticationProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthentikAuthenticationProviderPort implements AuthenticationProviderPort {

    private final AuthentikClient client;

    private final AuthentikAuthenticationProviderPortMapper mapper;

    @Override
    public ExternalProviderUserModel create(CreateUserRequestModel request) {
        final var existingUsers = client.findUsersByEmail(request.getEmail());

        if (existingUsers.getResults().isEmpty()) {
            final var user = client.createUser(request.getEmail());
            client.sendUserResetPassword(user.getId());
            return mapper.mapToModel(user);
        }

        final var existingUser = existingUsers.getResults().getFirst();
        final var updatedUser = client.updateUserGroup(existingUser, true);
        return mapper.mapToModel(updatedUser);
    }

    /**
     * Since Authentik is used for multiple applications (SSO) we control access through the user groups
     * we can't just enable/disable user because it can affect other applications
     * so we should add or remove the group of our application
     */
    @Override
    public void updateStatus(String id, Boolean active) {
        final var user = client.findUserById(id);
        client.updateUserGroup(user, active);
    }
}
