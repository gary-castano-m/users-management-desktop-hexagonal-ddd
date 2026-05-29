package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetAsignaturaByIdQuery;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetAsignaturaByIdUseCase {
    AsignaturaModel execute(@NotNull @Valid GetAsignaturaByIdQuery query);
}