package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteUserUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllUsersUseCase;
import com.jcaa.usersmanagement.application.port.in.GetUserByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateUserUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.CreateUserRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.ErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.LoginRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.MessageResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.UpdateUserRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.UserRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.UserRestMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class UserRestController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final LoginUseCase loginUseCase;

    public void registerRoutes(final Javalin app) {
        app.post("/api/users/login",  this::login);
        app.post("/api/users",        this::create);
        app.get("/api/users",         this::listAll);
        app.get("/api/users/{id}",    this::findById);
        app.put("/api/users/{id}",    this::update);
        app.delete("/api/users/{id}", this::delete);
    }

    private void login(final Context ctx) {
        try {
            final LoginRestRequest request = ctx.bodyAsClass(LoginRestRequest.class);
            final UserRestResponse response =
                    UserRestMapper.toResponse(loginUseCase.execute(UserRestMapper.toLoginCommand(request)));
            ctx.status(200).json(response);
        } catch (final ConstraintViolationException ex) {
            ctx.status(400).json(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
        } catch (final RuntimeException ex) {
            ctx.status(401).json(new ErrorResponse("UNAUTHORIZED", ex.getMessage()));
        }
    }

    private void create(final Context ctx) {
        try {
            final CreateUserRestRequest request = ctx.bodyAsClass(CreateUserRestRequest.class);
            final UserRestResponse response =
                    UserRestMapper.toResponse(createUserUseCase.execute(UserRestMapper.toCreateCommand(request)));
            ctx.status(201).json(response);
        } catch (final ConstraintViolationException ex) {
            ctx.status(400).json(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
        } catch (final RuntimeException ex) {
            ctx.status(409).json(new ErrorResponse("CONFLICT", ex.getMessage()));
        }
    }

    private void listAll(final Context ctx) {
        final List<UserRestResponse> response =
                UserRestMapper.toResponseList(getAllUsersUseCase.execute());
        ctx.status(200).json(response);
    }

    private void findById(final Context ctx) {
        try {
            final String id = ctx.pathParam("id");
            final UserRestResponse response =
                    UserRestMapper.toResponse(getUserByIdUseCase.execute(UserRestMapper.toGetByIdQuery(id)));
            ctx.status(200).json(response);
        } catch (final RuntimeException ex) {
            ctx.status(404).json(new ErrorResponse("NOT_FOUND", ex.getMessage()));
        }
    }

    private void update(final Context ctx) {
        try {
            final String id = ctx.pathParam("id");
            final UpdateUserRestRequest request = ctx.bodyAsClass(UpdateUserRestRequest.class);
            final UserRestResponse response =
                    UserRestMapper.toResponse(updateUserUseCase.execute(UserRestMapper.toUpdateCommand(id, request)));
            ctx.status(200).json(response);
        } catch (final ConstraintViolationException ex) {
            ctx.status(400).json(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
        } catch (final RuntimeException ex) {
            ctx.status(409).json(new ErrorResponse("CONFLICT", ex.getMessage()));
        }
    }

    private void delete(final Context ctx) {
        final String id = ctx.pathParam("id");
        deleteUserUseCase.execute(UserRestMapper.toDeleteCommand(id));
        ctx.status(204);
    }
}
