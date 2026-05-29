package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.AsignaturaId;

public interface DeleteAsignaturaPort {
    void delete(AsignaturaId id);
}