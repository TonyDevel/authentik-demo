package com.authentik.demo.user.controller.mapper;

import com.authentik.demo.user.controller.dto.CreateUserRequestDto;
import com.authentik.demo.user.controller.dto.UpdateUserRequestDto;
import com.authentik.demo.user.controller.dto.UserDto;
import com.authentik.demo.user.domain.model.CreateUserRequestModel;
import com.authentik.demo.user.domain.model.UpdateUserRequestModel;
import com.authentik.demo.user.domain.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestUserControllerMapper {

    UserDto mapFromModel(UserModel user);

    CreateUserRequestModel mapCreateRequestToModel(CreateUserRequestDto request);

    UpdateUserRequestModel mapUpdateRequestToModel(UpdateUserRequestDto request);
}
