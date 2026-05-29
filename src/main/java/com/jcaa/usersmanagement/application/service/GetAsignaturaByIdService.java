package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAsignaturaByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAsignaturaByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetAsignaturaByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.AsignaturaApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.AsignaturaNotFoundException;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAsignaturaByIdService implements GetAsignaturaByIdUseCase {

    private final GetAsignaturaByIdPort getAsignaturaByIdPort;
    private final Validator validator;

    @Override
    public AsignaturaModel execute(final GetAsignaturaByIdQuery query) {
        validateQuery(query);

        final AsignaturaId asignaturaId =
                AsignaturaApplicationMapper.fromGetAsignaturaByIdQueryToAsignaturaId(query);

        return getAsignaturaByIdPort
                .getById(asignaturaId)
                .orElseThrow(
                        () -> AsignaturaNotFoundException.becauseIdWasNotFound(asignaturaId.value()));
    }

    private void validateQuery(final GetAsignaturaByIdQuery query) {
        final Set<ConstraintViolation<GetAsignaturaByIdQuery>> violations =
                validator.validate(query);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
