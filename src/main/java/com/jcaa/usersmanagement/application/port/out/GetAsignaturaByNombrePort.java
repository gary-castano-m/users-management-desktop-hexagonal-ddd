package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaNombre;
import java.util.Optional;

public interface GetAsignaturaByNombrePort {
    Optional<AsignaturaModel> getByNombre(AsignaturaNombre nombre);
}