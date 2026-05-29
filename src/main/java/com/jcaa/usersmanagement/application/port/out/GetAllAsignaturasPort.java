package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import java.util.List;

public interface GetAllAsignaturasPort {
    List<AsignaturaModel> getAll();
}
