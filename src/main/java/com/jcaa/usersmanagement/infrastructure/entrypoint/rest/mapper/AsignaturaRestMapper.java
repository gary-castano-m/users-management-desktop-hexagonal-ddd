package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetAsignaturaByIdQuery;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.AsignaturaRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.CreateAsignaturaRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.UpdateAsignaturaRestRequest;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AsignaturaRestMapper {

    public CreateAsignaturaCommand toCreateCommand(final CreateAsignaturaRestRequest request) {
        return new CreateAsignaturaCommand(
                request.id(),
                request.nombre(),
                request.nombreCompleto(),
                request.descripcion(),
                request.areaConocimiento(),
                request.carrera(),
                request.numeroCreditos(),
                request.contenidoTematico(),
                request.semestre(),
                request.profesor());
    }

    public UpdateAsignaturaCommand toUpdateCommand(
            final String id, final UpdateAsignaturaRestRequest request) {
        return new UpdateAsignaturaCommand(
                id,
                request.nombre(),
                request.nombreCompleto(),
                request.descripcion(),
                request.areaConocimiento(),
                request.carrera(),
                request.numeroCreditos(),
                request.contenidoTematico(),
                request.semestre(),
                request.profesor());
    }

    public DeleteAsignaturaCommand toDeleteCommand(final String id) {
        return new DeleteAsignaturaCommand(id);
    }

    public GetAsignaturaByIdQuery toGetByIdQuery(final String id) {
        return new GetAsignaturaByIdQuery(id);
    }

    public AsignaturaRestResponse toResponse(final AsignaturaModel asignatura) {
        return new AsignaturaRestResponse(
                asignatura.getId().value(),
                asignatura.getNombre().value(),
                asignatura.getNombreCompleto().value(),
                asignatura.getDescripcion().value(),
                asignatura.getAreaConocimiento().value(),
                asignatura.getCarrera().value(),
                asignatura.getNumeroCreditos().value(),
                asignatura.getContenidoTematico().value(),
                asignatura.getSemestre().value(),
                asignatura.getProfesor().value());
    }

    public List<AsignaturaRestResponse> toResponseList(final List<AsignaturaModel> asignaturas) {
        return asignaturas.stream().map(AsignaturaRestMapper::toResponse).toList();
    }
}