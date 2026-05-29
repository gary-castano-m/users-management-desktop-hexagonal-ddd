package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteAsignaturaCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteAsignaturaUseCase {
    void execute(@NotNull @Valid DeleteAsignaturaCommand command);
}