package com.authentik.demo.user.domain.usecase;

import com.authentik.demo.user.domain.model.CreateUserRequestModel;
import com.authentik.demo.user.domain.model.UpdateUserRequestModel;
import com.authentik.demo.user.domain.model.UserModel;
import com.authentik.demo.user.domain.port.AuthenticationProviderPort;
import com.authentik.demo.user.domain.port.UserStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStoragePort storagePort;

    private final AuthenticationProviderPort authenticationProviderPort;

    public UserModel create(CreateUserRequestModel request) {
        final var externalUser = authenticationProviderPort.create(request);

        return storagePort.create(request, externalUser.getId());
    }

    public UserModel update(UpdateUserRequestModel request) {
        final var existingUser = storagePort.findById(request.getId());
        authenticationProviderPort.updateStatus(existingUser.getExternalId(), request.getActive());
        return storagePort.update(request);
    }

    public Collection<UserModel> findAll() {
        return storagePort.findAll();
    }
}
