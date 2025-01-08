package com.authentik.demo.user.domain.port;

import com.authentik.demo.user.domain.model.CreateUserRequestModel;
import com.authentik.demo.user.domain.model.UpdateUserRequestModel;
import com.authentik.demo.user.domain.model.UserModel;

import java.util.Collection;
import java.util.UUID;

public interface UserStoragePort {
    UserModel create(CreateUserRequestModel request, String externalId);

    UserModel update(UpdateUserRequestModel request);

    Collection<UserModel> findAll();

    UserModel findById(UUID id);
}
