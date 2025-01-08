package com.authentik.demo.user.controller;

import com.authentik.demo.user.controller.dto.UpdateUserRequestDto;
import com.authentik.demo.user.domain.usecase.UserService;
import com.authentik.demo.user.controller.dto.CreateUserRequestDto;
import com.authentik.demo.user.controller.dto.UserDto;
import com.authentik.demo.user.controller.mapper.RestUserControllerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    private final RestUserControllerMapper mapper;

    @PostMapping
    public UserDto create(@RequestBody @Valid CreateUserRequestDto request) {
        return mapper.mapFromModel(
            userService.create(
                mapper.mapCreateRequestToModel(request)
            )
        );
    }

    @PutMapping
    public UserDto update(@RequestBody @Valid UpdateUserRequestDto request) {
        return mapper.mapFromModel(
            userService.update(
                mapper.mapUpdateRequestToModel(request)
            )
        );
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll()
            .stream()
            .map(mapper::mapFromModel)
            .toList();
    }
}
