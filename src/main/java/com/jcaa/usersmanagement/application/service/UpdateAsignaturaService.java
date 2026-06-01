package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateAsignaturaUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAsignaturaByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetAsignaturaByNombrePort;
import com.jcaa.usersmanagement.application.port.out.UpdateAsignaturaPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.mapper.AsignaturaApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.AsignaturaAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.AsignaturaNotFoundException;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaId;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaNombre;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateAsignaturaService implements UpdateAsignaturaUseCase {

    private final UpdateAsignaturaPort updateAsignaturaPort;
    private final GetAsignaturaByIdPort getAsignaturaByIdPort;
    private final GetAsignaturaByNombrePort getAsignaturaByNombrePort;
    private final Validator validator;

    @Override
    public AsignaturaModel execute(final UpdateAsignaturaCommand command) {
        validateCommand(command);

        final AsignaturaId asignaturaId = new AsignaturaId(command.id());
        final AsignaturaModel current = findExistingAsignaturaOrFail(asignaturaId);

        final AsignaturaNombre newNombre = new AsignaturaNombre(command.nombre());
        ensureNombreIsNotTakenByAnotherAsignatura(newNombre, asignaturaId);

        final AsignaturaModel asignaturaToUpdate =
                AsignaturaApplicationMapper.fromUpdateCommandToModel(command);

        if (!hasDataChanged(current, asignaturaToUpdate)) {
            return current;
        }

        return updateAsignaturaPort.update(asignaturaToUpdate);
    }

    private void validateCommand(final UpdateAsignaturaCommand command) {
        final Set<ConstraintViolation<UpdateAsignaturaCommand>> violations =
                validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private AsignaturaModel findExistingAsignaturaOrFail(final AsignaturaId asignaturaId) {
        return getAsignaturaByIdPort
                .getById(asignaturaId)
                .orElseThrow(
                        () -> AsignaturaNotFoundException.becauseIdWasNotFound(asignaturaId.value()));
    }

    private void ensureNombreIsNotTakenByAnotherAsignatura(
            final AsignaturaNombre newNombre, final AsignaturaId ownerId) {
        getAsignaturaByNombrePort
                .getByNombre(newNombre)
                .ifPresent(
                        found -> {
                            if (!found.getId().equals(ownerId)) {
                                throw AsignaturaAlreadyExistsException.becauseNombreAlreadyExists(
                                        newNombre.value());
                            }
                        });
    }

    private boolean hasDataChanged(final AsignaturaModel current, final AsignaturaModel updated) {
        return !Objects.equals(current.getNombre(), updated.getNombre())
                || !Objects.equals(current.getNombreCompleto(), updated.getNombreCompleto())
                || !Objects.equals(current.getDescripcion(), updated.getDescripcion())
                || !Objects.equals(current.getAreaConocimiento(), updated.getAreaConocimiento())
                || !Objects.equals(current.getCarrera(), updated.getCarrera())
                || !Objects.equals(current.getNumeroCreditos(), updated.getNumeroCreditos())
                || !Objects.equals(current.getContenidoTematico(), updated.getContenidoTematico())
                || !Objects.equals(current.getSemestre(), updated.getSemestre())
                || !Objects.equals(current.getProfesor(), updated.getProfesor());
    }
}