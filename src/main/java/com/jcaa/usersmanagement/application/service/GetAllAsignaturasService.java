package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllAsignaturasUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllAsignaturasPort;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAllAsignaturasService implements GetAllAsignaturasUseCase {

    private final GetAllAsignaturasPort getAllAsignaturasPort;

    @Override
    public List<AsignaturaModel> execute() {
        return getAllAsignaturasPort.getAll();
    }
}
