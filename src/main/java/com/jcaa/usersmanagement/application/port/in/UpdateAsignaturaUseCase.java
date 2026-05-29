package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateAsignaturaCommand;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateAsignaturaUseCase {
    AsignaturaModel execute(@NotNull @Valid UpdateAsignaturaCommand command);
}