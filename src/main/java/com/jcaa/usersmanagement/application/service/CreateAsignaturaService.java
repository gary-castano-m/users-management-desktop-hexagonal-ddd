package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateAsignaturaUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAsignaturaByNombrePort;
import com.jcaa.usersmanagement.application.port.out.SaveAsignaturaPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.mapper.AsignaturaApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.AsignaturaAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaNombre;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateAsignaturaService implements CreateAsignaturaUseCase {

    private final SaveAsignaturaPort saveAsignaturaPort;
    private final GetAsignaturaByNombrePort getAsignaturaByNombrePort;
    private final Validator validator;

    @Override
    public AsignaturaModel execute(final CreateAsignaturaCommand command) {
        validateCommand(command);

        final AsignaturaNombre nombre = new AsignaturaNombre(command.nombre());
        ensureNombreIsNotTaken(nombre);

        final AsignaturaModel asignaturaToSave =
                AsignaturaApplicationMapper.fromCreateCommandToModel(command);

        return saveAsignaturaPort.save(asignaturaToSave);
    }

    private void validateCommand(final CreateAsignaturaCommand command) {
        final Set<ConstraintViolation<CreateAsignaturaCommand>> violations =
                validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureNombreIsNotTaken(final AsignaturaNombre nombre) {
        getAsignaturaByNombrePort
                .getByNombre(nombre)
                .ifPresent(
                        ignored -> {
                            throw AsignaturaAlreadyExistsException.becauseNombreAlreadyExists(nombre.value());
                        });
    }
}
