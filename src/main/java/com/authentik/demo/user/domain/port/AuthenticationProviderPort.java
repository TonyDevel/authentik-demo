package com.authentik.demo.user.domain.port;

import com.authentik.demo.user.domain.model.CreateUserRequestModel;
import com.authentik.demo.user.domain.model.ExternalProviderUserModel;

public interface AuthenticationProviderPort {
    ExternalProviderUserModel create(CreateUserRequestModel request);

    void updateStatus(String id, Boolean active);
}
