package com.authentik.demo.user.adapter.auth_provider.mapper;

import com.authentik.demo.client.dto.AuthentikUserResponse;
import com.authentik.demo.user.domain.model.ExternalProviderUserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthentikAuthenticationProviderPortMapper {
    ExternalProviderUserModel mapToModel(AuthentikUserResponse user);
}
