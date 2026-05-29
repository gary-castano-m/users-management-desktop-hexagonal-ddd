package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import java.util.List;

public interface GetAllAsignaturasUseCase {
    List<AsignaturaModel> execute();
}
