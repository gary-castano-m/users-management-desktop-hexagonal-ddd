package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteAsignaturaUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteAsignaturaPort;
import com.jcaa.usersmanagement.application.port.out.GetAsignaturaByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.mapper.AsignaturaApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.AsignaturaNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteAsignaturaService implements DeleteAsignaturaUseCase {

    private final DeleteAsignaturaPort deleteAsignaturaPort;
    private final GetAsignaturaByIdPort getAsignaturaByIdPort;
    private final Validator validator;

    @Override
    public void execute(final DeleteAsignaturaCommand command) {
        validateCommand(command);

        final AsignaturaId asignaturaId =
                AsignaturaApplicationMapper.fromDeleteCommandToAsignaturaId(command);

        ensureAsignaturaExists(asignaturaId);
        deleteAsignaturaPort.delete(asignaturaId);
    }

    private void validateCommand(final DeleteAsignaturaCommand command) {
        final Set<ConstraintViolation<DeleteAsignaturaCommand>> violations =
                validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureAsignaturaExists(final AsignaturaId asignaturaId) {
        getAsignaturaByIdPort
                .getById(asignaturaId)
                .orElseThrow(
                        () -> AsignaturaNotFoundException.becauseIdWasNotFound(asignaturaId.value()));
    }
}
