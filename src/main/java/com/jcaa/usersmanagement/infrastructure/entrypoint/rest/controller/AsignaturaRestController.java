package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateAsignaturaUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteAsignaturaUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllAsignaturasUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAsignaturaByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateAsignaturaUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.AsignaturaRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.CreateAsignaturaRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.ErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.UpdateAsignaturaRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.AsignaturaRestMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class AsignaturaRestController {

    private final CreateAsignaturaUseCase createAsignaturaUseCase;
    private final UpdateAsignaturaUseCase updateAsignaturaUseCase;
    private final DeleteAsignaturaUseCase deleteAsignaturaUseCase;
    private final GetAsignaturaByIdUseCase getAsignaturaByIdUseCase;
    private final GetAllAsignaturasUseCase getAllAsignaturasUseCase;

    public void registerRoutes(final Javalin app) {
        app.post("/api/asignaturas",        this::create);
        app.get("/api/asignaturas",         this::listAll);
        app.get("/api/asignaturas/{id}",    this::findById);
        app.put("/api/asignaturas/{id}",    this::update);
        app.delete("/api/asignaturas/{id}", this::delete);
    }

    private void create(final Context ctx) {
        try {
            final CreateAsignaturaRestRequest request = ctx.bodyAsClass(CreateAsignaturaRestRequest.class);
            final AsignaturaRestResponse response =
                    AsignaturaRestMapper.toResponse(createAsignaturaUseCase.execute(AsignaturaRestMapper.toCreateCommand(request)));
            ctx.status(201).json(response);
        } catch (final ConstraintViolationException ex) {
            ctx.status(400).json(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
        } catch (final RuntimeException ex) {
            ctx.status(409).json(new ErrorResponse("CONFLICT", ex.getMessage()));
        }
    }

    private void listAll(final Context ctx) {
        final List<AsignaturaRestResponse> response =
                AsignaturaRestMapper.toResponseList(getAllAsignaturasUseCase.execute());
        ctx.status(200).json(response);
    }

    private void findById(final Context ctx) {
        try {
            final String id = ctx.pathParam("id");
            final AsignaturaRestResponse response =
                    AsignaturaRestMapper.toResponse(getAsignaturaByIdUseCase.execute(AsignaturaRestMapper.toGetByIdQuery(id)));
            ctx.status(200).json(response);
        } catch (final RuntimeException ex) {
            ctx.status(404).json(new ErrorResponse("NOT_FOUND", ex.getMessage()));
        }
    }

    private void update(final Context ctx) {
        try {
            final String id = ctx.pathParam("id");
            final UpdateAsignaturaRestRequest request = ctx.bodyAsClass(UpdateAsignaturaRestRequest.class);
            final AsignaturaRestResponse response =
                    AsignaturaRestMapper.toResponse(updateAsignaturaUseCase.execute(AsignaturaRestMapper.toUpdateCommand(id, request)));
            ctx.status(200).json(response);
        } catch (final ConstraintViolationException ex) {
            ctx.status(400).json(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
        } catch (final RuntimeException ex) {
            ctx.status(409).json(new ErrorResponse("CONFLICT", ex.getMessage()));
        }
    }

    private void delete(final Context ctx) {
        final String id = ctx.pathParam("id");
        deleteAsignaturaUseCase.execute(AsignaturaRestMapper.toDeleteCommand(id));
        ctx.status(204);
    }
}