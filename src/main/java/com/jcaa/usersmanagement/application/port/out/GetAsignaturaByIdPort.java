package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaId;
import java.util.Optional;

public interface GetAsignaturaByIdPort {
    Optional<AsignaturaModel> getById(AsignaturaId id);
}
