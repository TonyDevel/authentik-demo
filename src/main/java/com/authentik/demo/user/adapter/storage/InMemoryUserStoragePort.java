package com.authentik.demo.user.adapter.storage;

import com.authentik.demo.user.domain.model.CreateUserRequestModel;
import com.authentik.demo.user.domain.model.UpdateUserRequestModel;
import com.authentik.demo.user.domain.model.UserModel;
import com.authentik.demo.user.domain.port.UserStoragePort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InMemoryUserStoragePort implements UserStoragePort {

    private final Map<UUID, UserModel> users = new HashMap<>();

    @Override
    public UserModel create(CreateUserRequestModel request, String externalId) {
        final var user = UserModel.builder()
            .id(UUID.randomUUID())
            .email(request.getEmail())
            .externalId(externalId)
            .email(request.getEmail())
            .active(request.getActive())
            .build();

        return users.put(user.getId(), user);
    }

    @Override
    public UserModel update(UpdateUserRequestModel request) {
        final var user = users.get(request.getId());

        final var updatedUser = user.toBuilder()
            .active(request.getActive())
            .build();

        users.put(updatedUser.getId(), updatedUser);

        return updatedUser;
    }

    @Override
    public Collection<UserModel> findAll() {
        return users.values();
    }

    @Override
    public UserModel findById(UUID id) {
        return users.get(id);
    }
}
